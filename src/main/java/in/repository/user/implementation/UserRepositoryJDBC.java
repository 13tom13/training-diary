package in.repository.user.implementation;

import exceptions.RepositoryException;
import in.repository.user.UserRepository;
import model.Rights;
import model.Roles;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJDBC implements UserRepository {

    private final Connection connection;

    public UserRepositoryJDBC(Connection connection) {
        this.connection = connection;
    }

    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = getUser(resultSet);
                    if (user.getRoles() == null) {
                        user.setRoles(new ArrayList<>()); // Инициализация списка ролей, если он еще не был инициализирован
                    }
                    List<Roles> userRolesById = getUserRolesById(user.getId());
                    user.setRoles(userRolesById);
                    if (user.getRights() == null) {
                        user.setRights(new ArrayList<>()); // Инициализация списка прав, если он еще не был инициализирован
                    }
                    user.setRights(getUserRightsById(user.getId()));
                    connection.commit();
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackConnection();
        }
        return Optional.empty();
    }
    private void rollbackConnection() {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = getUser(resultSet);
                user.setRights(getUserRightsById(user.getId()));
                user.setRoles(getUserRolesById(user.getId()));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<Rights> getAllRights() {
        List<Rights> rights = new ArrayList<>();
        String sql = "SELECT * FROM rights";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Rights right = new Rights(id, name);
                rights.add(right);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rights;
    }


    @Override
    public void saveUser(User user) throws RepositoryException {
        String sql = "INSERT INTO users (email, first_name, last_name, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPassword());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new RepositoryException("Failed to add user");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new RepositoryException("Failed to add user, no ID obtained.");
                }
            }
            insertUserRights(user);
            insertUserRoles(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RepositoryException("Database connection error");
        }
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, password = ? WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            statement.executeUpdate();
            updateUserRights(user);
            updateUserRoles(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User user) {
        String sql = "DELETE FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.executeUpdate();
            deleteUserRights(user);
            deleteUserRoles(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для создания объекта User из ResultSet
    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }

    // Метод для получения списка прав пользователя по его ID
    private List<Rights> getUserRightsById(int userId) throws SQLException {
        List<Rights> rights = new ArrayList<>();
        String sql = "SELECT r.id, r.name FROM rights r INNER JOIN user_rights ur ON r.id = ur.right_id WHERE ur.user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    Rights right = new Rights(id, name);
                    rights.add(right);
                }
            }
        }
        return rights;
    }

    // Метод для получения списка ролей пользователя по его ID
    private List<Roles> getUserRolesById(int userId) throws SQLException {
        List<Roles> roles = new ArrayList<>();
        String sql = "SELECT r.id, r.name FROM roles r INNER JOIN users_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    Roles role = new Roles(id, name);
                    roles.add(role);
                }
            }
        }
        return roles;
    }

    // Метод для обновления прав пользователя в базе данных
    private void updateUserRights(User user) throws SQLException {
        deleteUserRights(user);
        insertUserRights(user);
    }

    // Метод для обновления ролей пользователя в базе данных
    private void updateUserRoles(User user) throws SQLException {
        deleteUserRoles(user);
        insertUserRoles(user);
    }

    // Метод для добавления прав пользователя в базу данных
    private void insertUserRights(User user) throws SQLException {
        String sql = "INSERT INTO user_rights (user_id, right_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Rights right : user.getRights()) {
                statement.setInt(1, user.getId());
                statement.setLong(2, right.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    // Метод для добавления ролей пользователя в базу данных
    private void insertUserRoles(User user) throws SQLException {
        String sql = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Roles role : user.getRoles()) {
                statement.setInt(1, user.getId());
                statement.setLong(2, role.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    // Метод для удаления прав пользователя из базы данных
    private void deleteUserRights(User user) throws SQLException {
        int userId = user.getId();
        String sql = "DELETE FROM user_rights WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }

    // Метод для удаления ролей пользователя из базы данных
    private void deleteUserRoles(User user) throws SQLException {
        int userId = user.getId();
        String sql = "DELETE FROM users_roles WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }
}

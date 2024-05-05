package in.repository.user.implementation;

import entities.model.User;
import exceptions.RepositoryException;
import in.repository.user.UserRepository;
import entities.model.Rights;
import entities.model.Roles;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJDBC implements UserRepository {
    /** Соединение с базой данных. */
    private final Connection connection;

    /**
     * Конструктор для создания объекта UserRepositoryJDBC с указанным соединением с базой данных.
     *
     * @param connection соединение с базой данных
     */
    public UserRepositoryJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Получает пользователя по электронной почте.
     *
     * @param email электронная почта пользователя
     * @return объект Optional, содержащий пользователя, если пользователь найден, иначе пустой Optional
     */
    @Override
    public Optional<User> getUserByEmail(String email) {
        String sql = """
             SELECT *
             FROM main.users
             WHERE email = ?
             """;

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
            rollbackConnection();
            System.err.println("Ошибка при выполнении запроса к базе данных");
        }
        return Optional.empty();
    }

    /**
     * Получает список всех пользователей.
     *
     * @return список всех пользователей
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = """
             SELECT *
             FROM main.users
             """;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = getUser(resultSet);
                user.setRights(getUserRightsById(user.getId()));
                user.setRoles(getUserRolesById(user.getId()));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении пользователей: " + e.getMessage());
        }
        return users;
    }

    /**
     * Получает список всех прав.
     *
     * @return список всех прав
     */
    @Override
    public List<Rights> getAllRights() {
        List<Rights> rights = new ArrayList<>();
        String sql = """
             SELECT *
             FROM permissions.rights
             """;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Rights right = new Rights(id, name);
                rights.add(right);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении прав пользователей: " + e.getMessage());
        }
        return rights;
    }

    /**
     * Сохраняет нового пользователя в базе данных.
     *
     * @param user пользователь для сохранения
     * @throws RepositoryException если возникла ошибка при выполнении запроса к базе данных
     */
    @Override
    public void saveUser(User user) throws RepositoryException {
        String sql = """
             INSERT INTO main.users (email, first_name, last_name, password)
             VALUES (?, ?, ?, ?)
             """;

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
            assignUserRights(user);
            assignUserRoles(user);
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при выполнении запроса к базе данных " + e.getMessage());
        }
    }

    /**
     * Обновляет информацию о пользователе в базе данных.
     *
     * @param user пользователь для обновления
     */
    @Override
    public void updateUser(User user) {
        System.out.println("user in repository: " + user);
        System.out.println(user.isActive());
        String sql = """
                UPDATE main.users 
                SET first_name = ?, last_name = ?, password = ?, is_active = ? 
                WHERE email = ?
                                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setBoolean(4, user.isActive());
            statement.setString(5, user.getEmail());
            System.out.println("запрос: " + statement.toString());
            int i = statement.executeUpdate();
            System.out.println("rows updated: " + i);
            System.out.println("user after update: " + getUserByEmail(user.getEmail()));
            updateUserRights(user);
            updateUserRoles(user);

        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении пользователя: " + e.getMessage());
        }
    }

    /**
     * Удаляет пользователя из базы данных.
     *
     * @param user пользователь для удаления
     */
    @Override
    public void deleteUser(User user) {
        String sql = """
             DELETE FROM main.users
             WHERE email = ?
             """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.executeUpdate();
            deleteUserRights(user);
            deleteUserRoles(user);
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    // Дополнительные приватные методы и метод rollbackConnection()

    /**
     * Создает объект пользователя из ResultSet.
     *
     * @param resultSet объект ResultSet, содержащий данные о пользователе
     * @return объект User
     * @throws SQLException если возникла ошибка при работе с ResultSet
     */
    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setPassword(resultSet.getString("password"));
        user.setActive(resultSet.getBoolean("is_active"));
        return user;
    }

    /**
     * Получает список прав пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return список прав пользователя
     * @throws SQLException если возникла ошибка при выполнении запроса к базе данных
     */
    private List<Rights> getUserRightsById(long userId) throws SQLException {
        List<Rights> rights = new ArrayList<>();
        String sql = """
             SELECT r.id, r.name
             FROM permissions.rights r
             INNER JOIN relations.user_rights ur ON r.id = ur.right_id
             WHERE ur.user_id = ?
             """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
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

    /**
     * Получает список ролей пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return список ролей пользователя
     * @throws SQLException если возникла ошибка при выполнении запроса к базе данных
     */
    private List<Roles> getUserRolesById(long userId) throws SQLException {
        List<Roles> roles = new ArrayList<>();
        String sql = """
             SELECT r.id, r.name
             FROM permissions.roles r
             INNER JOIN relations.users_roles ur ON r.id = ur.role_id
             WHERE ur.user_id = ?
             """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
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

    /**
     * Обновляет права пользователя в базе данных.
     *
     * @param user пользователь, чьи права нужно обновить
     * @throws SQLException если возникла ошибка при выполнении запроса к базе данных
     */
    private void updateUserRights(User user) throws SQLException {
        deleteUserRights(user);
        assignUserRights(user);
    }

    /**
     * Обновляет роли пользователя в базе данных.
     *
     * @param user пользователь, чьи роли нужно обновить
     * @throws SQLException если возникла ошибка при выполнении запроса к базе данных
     */
    private void updateUserRoles(User user) throws SQLException {
        deleteUserRoles(user);
        assignUserRoles(user);
    }

    /**
     * Вставляет права пользователя в базу данных.
     *
     * @param user пользователь, для которого нужно вставить права
     * @throws SQLException если возникла ошибка при выполнении запроса к базе данных
     */
    @Override
    public void assignUserRights(User user) throws SQLException {
        String sqlSelectRights = """
                        SELECT id
                        FROM permissions.rights
                        """;
        String sqlInsertRights = """
                         INSERT INTO relations.user_rights (user_id, right_id)
                         VALUES (?, ?)
                         """;
        try (PreparedStatement selectStatement = connection.prepareStatement(sqlSelectRights);
             PreparedStatement insertStatement = connection.prepareStatement(sqlInsertRights)) {

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                int rightId = resultSet.getInt("id");
                insertStatement.setLong(1, user.getId());
                insertStatement.setLong(2, rightId);
                insertStatement.executeUpdate();
            }
        }
    }

    /**
     * Вставляет роли пользователя в базу данных.
     *
     * @param user пользователь, для которого нужно вставить права
     * @throws SQLException если возникла ошибка при выполнении запроса к базе данных
     */
    @Override
    public void assignUserRoles(User user) throws SQLException {
        String sqlSelectRoles = """
                        SELECT id
                        FROM permissions.roles
                        WHERE name = ?
                        """;
        String sqlInsertUserRole = """
                           INSERT INTO relations.users_roles (user_id, role_id)
                           VALUES (?, ?)
                           """;

        try (PreparedStatement selectStatement = connection.prepareStatement(sqlSelectRoles);
             PreparedStatement insertStatement = connection.prepareStatement(sqlInsertUserRole)) {

            // Получаем id роли с именем "USER"
            selectStatement.setString(1, "USER");
            ResultSet resultSet = selectStatement.executeQuery();

            // Если роль уже существует в базе данных
            if (resultSet.next()) {
                long userRoleId = resultSet.getLong("id");
                // Вставляем роль "USER" для данного пользователя
                insertStatement.setLong(1, user.getId());
                insertStatement.setLong(2, userRoleId);
                insertStatement.executeUpdate();
            } else {
                // Если роль "USER" отсутствует в базе данных, выбросить исключение
                throw new SQLException("Роль USER не найдена в таблице ролей.");
            }
        }
    }


    /**
     * Удаляет права пользователя из базы данных.
     *
     * @param user пользователь, у которого нужно удалить права
     * @throws SQLException если возникла ошибка при выполнении запроса к базе данных
     */
    private void deleteUserRights(User user) throws SQLException {
        long userId = user.getId();
        String sql = """
             DELETE FROM relations.user_rights
             WHERE user_id = ?
             """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
        }
    }

    /**
     * Удаляет роли пользователя из базы данных.
     *
     * @param user пользователь, у которого нужно удалить роли
     * @throws SQLException если возникла ошибка при выполнении запроса к базе данных
     */
    private void deleteUserRoles(User user) throws SQLException {
        long userId = user.getId();
        String sql = """
             DELETE FROM relations.users_roles
             WHERE user_id = ?
             """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
        }
    }

    /**
     * Отменяет текущую транзакцию и выполняет откат изменений.
     */
    private void rollbackConnection() {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при попытке отмены транзакции: " + e.getMessage());
        }
    }
}

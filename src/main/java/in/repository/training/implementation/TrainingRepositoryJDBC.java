package in.repository.training.implementation;

import entity.model.Training;
import entity.model.User;
import exceptions.RepositoryException;
import in.repository.training.TrainingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Реализация интерфейса {@link TrainingRepository} для хранения тренировок.
 * Этот класс обеспечивает методы для сохранения, получения и удаления тренировок пользователя.
 */
@Repository
@RequiredArgsConstructor
@Transactional
public class TrainingRepositoryJDBC implements TrainingRepository {

    private final DataSource dataSource;

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TreeMap<LocalDate, TreeSet<Training>> getAllTrainingsByUserEmail(String email) {
        TreeMap<LocalDate, TreeSet<Training>> userTrainingMap = new TreeMap<>();
        String sql = """
                SELECT t.id AS training_id, t.name, t.date, t.duration, t.calories_burned,
                       ta.key AS addition_key, ta.value AS addition_value
                FROM main.trainings t
                JOIN relations.user_trainings ut ON t.id = ut.training_id
                JOIN main.users u ON u.id = ut.user_id
                LEFT JOIN main.training_additions ta ON t.id = ta.training_id
                WHERE u.email = ?
                """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    Training training = getTraining(resultSet);
                    userTrainingMap.computeIfAbsent(date, k -> new TreeSet<>()).add(training);
                }
            }
        } catch (SQLException | RepositoryException e) {
            throw new RuntimeException(e);
        }
        return userTrainingMap;
    }


    @Override
    public TreeSet<Training> getTrainingsByUserEmailAndData(String email, LocalDate trainingDate) throws RepositoryException {
        TreeSet<Training> trainingsOnDate = new TreeSet<>();
        String sql = """
                SELECT t.id AS training_id, t.name, t.date, t.duration, t.calories_burned
                FROM main.trainings t
                JOIN relations.user_trainings ut ON t.id = ut.training_id
                JOIN main.users u ON u.id = ut.user_id
                WHERE u.email = ? AND t.date = ?
                """;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setDate(2, Date.valueOf(trainingDate));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Training training = getTraining(resultSet);
                    trainingsOnDate.add(training);
                }
            }
            if (!trainingsOnDate.isEmpty()) {
                return trainingsOnDate;
            } else {
                throw new RepositoryException("Тренировка с датой " + trainingDate + " не найдена для пользователя с email "
                                              + email);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при получении тренировок пользователя с email "
                                          + email + " и датой " + trainingDate);
        }
    }

    @Override
    public Training getTrainingByUserEmailAndDataAndName(String email, LocalDate trainingDate, String trainingName) throws RepositoryException {
        String sql =
                """
                        SELECT t.id AS training_id, t.name, t.date, t.duration, t.calories_burned,
                               ta.key AS addition_key, ta.value AS addition_value
                        FROM main.trainings t
                        JOIN relations.user_trainings ut ON t.id = ut.training_id
                        JOIN main.users u ON u.id = ut.user_id
                        LEFT JOIN main.training_additions ta ON t.id = ta.training_id
                        WHERE u.email = ? AND t.date = ? AND t.name = ?
                        """;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setDate(2, Date.valueOf(trainingDate));
            statement.setString(3, trainingName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getTraining(resultSet);
                } else {
                    throw new RepositoryException("Тренировка с именем " + trainingName + " не найдена в тренировках с датой "
                                                  + trainingDate + " для пользователя с email " + email);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при получении тренировки по адресу электронной почты " + email + " и дате " +
                                          trainingDate + " и имени " + trainingName);
        }
    }

    @Override
    public Training saveTraining(User user, Training newTraining) throws RepositoryException {
        String sqlInsertTraining = """
                INSERT INTO main.trainings (name, date, duration, calories_burned)
                VALUES (?, ?, ?, ?)
                """;
        String sqlInsertUserTraining = """
                INSERT INTO relations.user_trainings (user_id, training_id)
                VALUES (?, ?)
                """;
        try (Connection connection = getConnection();
             PreparedStatement statementTraining = connection.prepareStatement(sqlInsertTraining, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement statementUserTraining = connection.prepareStatement(sqlInsertUserTraining)) {
            statementTraining.setString(1, newTraining.getName());
            statementTraining.setDate(2, Date.valueOf(newTraining.getDate()));
            statementTraining.setInt(3, newTraining.getDuration());
            statementTraining.setInt(4, newTraining.getCaloriesBurned());
            int rowsAffected = statementTraining.executeUpdate();
            if (rowsAffected == 0) {
                throw new RepositoryException("Ошибка при сохранении тренировки");
            }
            ResultSet generatedKeys = statementTraining.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new RepositoryException("Не удалось получить идентификатор сохраненной тренировки");
            }
            long trainingId = generatedKeys.getLong(1);
            statementUserTraining.setLong(1, user.getId());
            statementUserTraining.setLong(2, trainingId);
            statementUserTraining.executeUpdate();
            newTraining.setId(trainingId);
//            saveTrainingAdditions(trainingId, newTraining.getAdditions());
            System.out.println("new training id: " + newTraining.getId());
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка сохранения тренировки " + e.getMessage());
        }
        return newTraining;
    }

    @Override
    public void deleteTraining(User user, Training training) throws RepositoryException {
        Connection connection = getConnection();
        String sqlDeleteUserTraining = """
                DELETE FROM relations.user_trainings
                WHERE user_id = ? AND training_id = ?
                """;
        String sqlDeleteTraining = """
                DELETE FROM main.trainings
                WHERE id = ?
                """;
        try (PreparedStatement statementDeleteUserTraining = connection.prepareStatement(sqlDeleteUserTraining);
             PreparedStatement statementDeleteTraining = connection.prepareStatement(sqlDeleteTraining)) {
            statementDeleteUserTraining.setLong(1, user.getId());
            statementDeleteUserTraining.setLong(2, training.getId());
            int rowsAffectedUserTraining = statementDeleteUserTraining.executeUpdate();
            if (rowsAffectedUserTraining == 0) {
                throw new RepositoryException("Ошибка при удалении связи пользователя с тренировкой");
            }

            // Удаление самой тренировки
            statementDeleteTraining.setLong(1, training.getId());
            int rowsAffectedTraining = statementDeleteTraining.executeUpdate();
            if (rowsAffectedTraining == 0) {
                throw new RepositoryException("Тренировка для удаления не найдена");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при удалении тренировки");
        }
    }

    @Override
    public Training updateTraining(User user, Training updatingTraining) throws RepositoryException {
        System.out.println("training for update: " + updatingTraining);
        String sqlSelectUser = """
                SELECT *
                FROM main.users
                WHERE email = ?
                """;
        String sqlUpdateTraining = """
                UPDATE main.trainings
                SET name = ?, date = ?, duration = ?, calories_burned = ?
                WHERE id = ?
                """;
        String sqlDeleteAdditions = """
                DELETE FROM main.training_additions
                WHERE training_id = ?
                """;
        try (Connection connection = getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(sqlSelectUser);
             PreparedStatement updateStatement = connection.prepareStatement(sqlUpdateTraining);
             PreparedStatement deleteAdditionsStatement = connection.prepareStatement(sqlDeleteAdditions)) {
            selectStatement.setString(1, user.getEmail());
            ResultSet userResultSet = selectStatement.executeQuery();
            if (!userResultSet.next()) {
                throw new RepositoryException("Пользователь с email " + user.getEmail() + " не найден в базе данных");
            }
            updateStatement.setString(1, updatingTraining.getName());
            updateStatement.setDate(2, Date.valueOf(updatingTraining.getDate()));
            updateStatement.setInt(3, updatingTraining.getDuration());
            updateStatement.setInt(4, updatingTraining.getCaloriesBurned());
            updateStatement.setLong(5, updatingTraining.getId());
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RepositoryException("Тренировка для обновления не найдена");
            }

            deleteAdditionsStatement.setLong(1, updatingTraining.getId());
            deleteAdditionsStatement.executeUpdate();

            saveTrainingAdditions(updatingTraining.getId(), updatingTraining.getAdditions());

        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при обновлении тренировки");
        }
        return updatingTraining;
    }


    public void saveTrainingAdditions(long id, HashMap<String, String> additions) throws RepositoryException {
        System.out.println("saveTrainingAdditions: " + additions);
        String insertSql = """
                INSERT INTO main.training_additions (training_id, key, value)
                VALUES (?, ?, ?)
                ON CONFLICT (training_id, key) DO NOTHING
                """;
        try (Connection connection = getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            for (String key : additions.keySet()) {
                insertStatement.setLong(1, id);
                insertStatement.setString(2, key);
                insertStatement.setString(3, additions.get(key));
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при сохранении дополнений для тренировки " + additions);
        }
    }


    // Метод для создания объекта Training из ResultSet
    private Training getTraining(ResultSet resultSet) throws SQLException, RepositoryException {
        long id = resultSet.getLong("training_id");
        String name = resultSet.getString("name");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        int duration = resultSet.getInt("duration");
        int caloriesBurned = resultSet.getInt("calories_burned");
        HashMap<String, String> trainingAdditions = getTrainingAdditions(id);
        return Training.builder().id(id).name(name).date(date).duration(duration).caloriesBurned(caloriesBurned).additions(trainingAdditions).build();
    }


    @Override
    public HashMap<String, String> getTrainingAdditions(long trainingID) throws RepositoryException {
        Connection connection = getConnection();
        HashMap<String, String> additions = new HashMap<>();

        String sql = """
                SELECT key, value
                FROM main.training_additions
                WHERE training_id = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, trainingID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String key = resultSet.getString("key");
                    String value = resultSet.getString("value");
                    additions.put(key, value);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при получении дополнительной информации о тренировке");
        }

        return additions;
    }
}

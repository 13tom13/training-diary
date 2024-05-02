package in.repository.training.implementation;

import entities.model.User;
import exceptions.RepositoryException;
import in.repository.training.TrainingRepository;
import entities.model.Training;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Реализация интерфейса {@link TrainingRepository} для хранения тренировок.
 * Этот класс обеспечивает методы для сохранения, получения и удаления тренировок пользователя.
 */
public class TrainingRepositoryJDBC implements TrainingRepository {

    private final Connection connection;

    /**
     * Создает новый экземпляр класса TrainingRepositoryCollections.
     * Инициализирует внутреннюю структуру данных для хранения тренировок пользователей.
     */
    public TrainingRepositoryJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Получает все тренировки пользователя по его адресу электронной почты.
     * Если пользователь с указанным адресом не найден, возвращает пустую TreeMap.
     *
     * @param user адрес электронной почты пользователя
     * @return TreeMap, содержащий все тренировки пользователя
     */
    @Override
    public TreeMap<LocalDate, TreeSet<Training>> getAllTrainingsByUserEmail(User user) {
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

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Получаем данные о тренировке из ResultSet
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    // Создаем объект Training из ResultSet
                    Training training = getTraining(resultSet);
                    // Добавляем тренировку в TreeMap, используя дату в качестве ключа
                    userTrainingMap.computeIfAbsent(date, k -> new TreeSet<>()).add(training);
                }
            }
        } catch (SQLException | RepositoryException e) {
            throw new RuntimeException(e);
        }
        return userTrainingMap;
    }



    /**
     * Получает тренировки пользователя по его адресу электронной почты и дате тренировки.
     * Если пользователь с указанным адресом не найден или тренировка на указанную дату отсутствует,
     * выбрасывает исключение RepositoryException.
     *
     * @param user         адрес электронной почты пользователя
     * @param trainingDate дата тренировки
     * @return множество тренировок пользователя на указанную дату
     * @throws RepositoryException если тренировка не найдена или возникла ошибка при доступе к хранилищу
     */
    @Override
    public TreeSet<Training> getTrainingsByUserEmailAndData(User user, LocalDate trainingDate) throws RepositoryException {
        TreeSet<Training> trainingsOnDate = new TreeSet<>();
        String sql = """
                SELECT t.id AS training_id, t.name, t.date, t.duration, t.calories_burned
                FROM main.trainings t
                JOIN relations.user_trainings ut ON t.id = ut.training_id
                JOIN main.users u ON u.id = ut.user_id
                WHERE u.email = ? AND t.date = ?
                """;


        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setDate(2, Date.valueOf(trainingDate));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Создаем объект Training из ResultSet
                    Training training = getTraining(resultSet);
                    trainingsOnDate.add(training);
                }
            }
            if (!trainingsOnDate.isEmpty()) {
                return trainingsOnDate;
            } else {
                throw new RepositoryException("Тренировка с датой " + trainingDate + " не найдена для пользователя с email " + user.getEmail());
            }
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при получении тренировок пользователя с email " + user.getEmail() + " и датой " + trainingDate);
        }
    }


    /**
     * Получает тренировку пользователя по его адресу электронной почты, дате и имени тренировки.
     * Если пользователь с указанным адресом не найден, тренировка на указанную дату отсутствует
     * или тренировка с указанным именем не найдена, выбрасывает исключение RepositoryException.
     *
     * @param user         адрес электронной почты пользователя
     * @param trainingDate дата тренировки
     * @param trainingName имя тренировки
     * @return тренировка пользователя на указанную дату и с указанным именем
     * @throws RepositoryException если тренировка не найдена или возникла ошибка при доступе к хранилищу
     */
    @Override
    public Training getTrainingByUserEmailAndDataAndName(User user, LocalDate trainingDate, String trainingName) throws RepositoryException {
        String sql = """
            SELECT t.id AS training_id, t.name, t.date, t.duration, t.calories_burned,
                   ta.key AS addition_key, ta.value AS addition_value
            FROM main.trainings t
            JOIN relations.user_trainings ut ON t.id = ut.training_id
            JOIN main.users u ON u.id = ut.user_id
            LEFT JOIN main.training_additions ta ON t.id = ta.training_id
            WHERE u.email = ? AND t.date = ? AND t.name = ?
            """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setDate(2, Date.valueOf(trainingDate));
            statement.setString(3, trainingName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getTraining(resultSet);
                } else {
                    throw new RepositoryException("Тренировка с именем " + trainingName + " не найдена в тренировках с датой " + trainingDate + " для пользователя с email " + user.getEmail());
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при получении тренировки по адресу электронной почты " + user.getEmail() + " и дате " + trainingDate + " и имени " + trainingName);
        }
    }



    /**
     * Сохраняет новую тренировку пользователя.
     * Если для указанного пользователя уже существует тренировка на указанную дату с тем же именем,
     * выбрасывает исключение RepositoryException.
     *
     * @param user        пользователь, для которого сохраняется тренировка
     * @param newTraining новая тренировка пользователя
     * @return новая тренировка пользователя
     * @throws RepositoryException если тренировка уже существует или возникла ошибка при доступе к хранилищу
     */
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
        try (PreparedStatement statementTraining = connection.prepareStatement(sqlInsertTraining, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement statementUserTraining = connection.prepareStatement(sqlInsertUserTraining)) {

            // Вставка новой тренировки
            statementTraining.setString(1, newTraining.getName());
            statementTraining.setDate(2, Date.valueOf(newTraining.getDate()));
            statementTraining.setInt(3, newTraining.getDuration());
            statementTraining.setInt(4, newTraining.getCaloriesBurned());
            int rowsAffected = statementTraining.executeUpdate();
            if (rowsAffected == 0) {
                throw new RepositoryException("Ошибка при сохранении тренировки");
            }

            // Получение идентификатора новой тренировки
            ResultSet generatedKeys = statementTraining.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new RepositoryException("Не удалось получить идентификатор сохраненной тренировки");
            }
            long trainingId = generatedKeys.getLong(1);

            // Вставка записи в таблицу user_trainings
            statementUserTraining.setLong(1, user.getId());
            statementUserTraining.setLong(2, trainingId);
            statementUserTraining.executeUpdate();

            // Сохранение дополнительной информации о тренировке
            newTraining.setId(trainingId); // Устанавливаем идентификатор тренировки
            saveTrainingAdditionals(newTraining); // Сохраняем дополнительную информацию
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка сохранения тренировки " + e.getMessage());
        }
        return newTraining;
    }


    /**
     * Удаляет указанную тренировку пользователя.
     * Если тренировка для удаления не найдена на указанную дату или для указанного пользователя,
     * выбрасывает исключение RepositoryException.
     *
     * @param user     пользователь, для которого удаляется тренировка
     * @param training тренировка для удаления
     * @return true, если тренировка для удаления найдена, иначе false
     * @throws RepositoryException если тренировка для удаления не найдена или возникла ошибка при доступе к хранилищу
     */
    @Override
    public boolean deleteTraining(User user, Training training) throws RepositoryException {
        String sqlDeleteUserTraining = """
                DELETE FROM relations.user_trainings
                WHERE user_id = ? AND training_id = ?
                """;
        String sqlDeleteTraining = """
                DELETE FROM main.trainings
                WHERE id = ?
                """;
        // Установка автоматического подтверждения транзакции вручную
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement statementDeleteUserTraining = connection.prepareStatement(sqlDeleteUserTraining);
                 PreparedStatement statementDeleteTraining = connection.prepareStatement(sqlDeleteTraining)) {

                // Удаление связи пользователя с тренировкой
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

                // Подтверждение транзакции
                connection.commit();

                // Возвращаем true, если удаление прошло успешно
                return true;
            } catch (SQLException e) {
                // Откатываем транзакцию в случае ошибки
                connection.rollback();
                throw new RepositoryException("Ошибка при удалении тренировки");
            } finally {
                // Восстановление режима автоматического подтверждения транзакции
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при установке режима автоматического подтверждения транзакции");
        }
    }


    /**
     * Обновляет указанную тренировку пользователя.
     * Если тренировка для обновления не найдена на указанную дату или для указанного пользователя,
     * выбрасывает исключение RepositoryException.
     *
     * @param user        адрес электронной почты пользователя
     * @param oldTraining тренировка для обновления
     * @param newTraining новая версия тренировки
     * @return измененная тренировка
     * @throws RepositoryException если тренировка для обновления не найдена или возникла ошибка при доступе к хранилищу
     */
    @Override
    public Training updateTraining(User user, Training oldTraining, Training newTraining) throws RepositoryException {
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
        String sqlDeleteAdditionals = """
                DELETE FROM main.training_additions
                WHERE training_id = ?
                """;
        try (PreparedStatement selectStatement = connection.prepareStatement(sqlSelectUser);
             PreparedStatement updateStatement = connection.prepareStatement(sqlUpdateTraining);
             PreparedStatement deleteAdditionalsStatement = connection.prepareStatement(sqlDeleteAdditionals)) {

            // Проверяем существование пользователя
            selectStatement.setString(1, user.getEmail());
            ResultSet userResultSet = selectStatement.executeQuery();
            if (!userResultSet.next()) {
                throw new RepositoryException("Пользователь с email " + user.getEmail() + " не найден в базе данных");
            }

            // Обновляем тренировку
            connection.setAutoCommit(false); // Начинаем транзакцию
            try {
                updateStatement.setString(1, newTraining.getName());
                updateStatement.setDate(2, Date.valueOf(newTraining.getDate()));
                updateStatement.setInt(3, newTraining.getDuration());
                updateStatement.setInt(4, newTraining.getCaloriesBurned());
                updateStatement.setLong(5, oldTraining.getId()); // Предполагается, что у oldTraining есть id
                int rowsAffected = updateStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new RepositoryException("Тренировка для обновления не найдена");
                }

                // Удаляем существующие дополнения к тренировке
                deleteAdditionalsStatement.setLong(1, oldTraining.getId());
                deleteAdditionalsStatement.executeUpdate();

                // Сохраняем новые дополнения
                saveTrainingAdditionals(newTraining);

                connection.commit(); // Фиксируем изменения в базе данных
            } catch (SQLException e) {
                connection.rollback(); // Откатываем изменения в случае ошибки
                throw new RepositoryException("Ошибка при обновлении тренировки");
            } finally {
                connection.setAutoCommit(true); // Возвращаем автоматический режим фиксации изменений
            }

        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при обновлении тренировки");
        }
        return newTraining;
    }


    public void saveTrainingAdditionals(Training training) throws RepositoryException {
        HashMap<String, String> additionals = training.getAdditions();
        if (additionals.isEmpty()) {
            return; // Если дополнительной информации нет, нет смысла сохранять
        }
        try {
            // Удаляем существующие записи о дополнительной информации
            deleteTrainingAdditionals(training.getId());
            String insertSql = """
                   INSERT INTO main.training_additions (training_id, key, value)
                   VALUES (?, ?, ?)
                   """;
            try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                connection.setAutoCommit(false);
                for (String key : additionals.keySet()) {
                    insertStatement.setLong(1, training.getId());
                    insertStatement.setString(2, key);
                    insertStatement.setString(3, additionals.get(key));
                    insertStatement.addBatch();
                }
                insertStatement.executeBatch();
                connection.commit(); // Фиксируем изменения в базе данных
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RepositoryException("Ошибка при откате изменений в базе данных");
            }
            throw new RepositoryException("Ошибка при сохранении дополнений для тренировки " + training.getName());
        }
    }


    public void deleteTrainingAdditionals(long trainingId) throws RepositoryException {
        String deleteSql = """
                   DELETE FROM main.training_additions
                   WHERE training_id = ?
                   """;
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setLong(1, trainingId);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при удалении дополнительной информации для тренировки с ID " + trainingId);
        }
    }


    // Метод для создания объекта Training из ResultSet
    private Training getTraining(ResultSet resultSet) throws SQLException, RepositoryException {
        long id = resultSet.getLong("training_id");
        String name = resultSet.getString("name");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        int duration = resultSet.getInt("duration");
        int caloriesBurned = resultSet.getInt("calories_burned");

        // Получаем дополнительную информацию о тренировке из ResultSet
        HashMap<String, String> trainingAdditionals = getTrainingAdditionals(id);

        return new Training(id, name, date, duration, caloriesBurned, trainingAdditionals);
    }


    private HashMap<String, String> getTrainingAdditionals(long trainingID) throws RepositoryException {
        HashMap<String, String> additionals = new HashMap<>();

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
                    additionals.put(key, value);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка при получении дополнительной информации о тренировке");
        }

        return additionals;
    }
}

package in.repository.trainingtype.implementation;

import in.repository.trainingtype.TrainingTypeRepository;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация интерфейса {@link TrainingTypeRepository} для хранения типов тренировок в базе данных.
 */
public class TrainingTypeRepositoryJDBC implements TrainingTypeRepository {

    private final Connection connection;

    /**
     * Конструктор класса TrainingTypeRepositoryJDBC.
     * Инициализирует подключение к базе данных.
     *
     * @param connection объект Connection для подключения к базе данных
     */
    public TrainingTypeRepositoryJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Получает список тренировок для указанного пользователя.
     * Если у пользователя нет собственного списка, возвращает список по умолчанию.
     *
     * @param user пользователь
     * @return список тренировок пользователя
     */
    @Override
    public List<String> getTrainingTypes(User user) {
        List<String> userTrainingTypes = new ArrayList<>();
        String sql = """
                SELECT training_type
                FROM permissions.user_training_types
                WHERE user_id = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userTrainingTypes.add(resultSet.getString("training_type"));
            }
            if (userTrainingTypes.isEmpty()) {
                return getDefaultTrainingTypes(user);
            } else {
                return userTrainingTypes;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Добавляет тип тренировки для указанного пользователя.
     * Если у пользователя еще нет списка тренировок, создает его на основе списка по умолчанию.
     *
     * @param user         пользователь
     * @param trainingType тип тренировки для добавления
     */
    @Override
    public void saveTrainingType(User user, String trainingType) {
        String sql = """
                INSERT INTO permissions.user_training_types (user_id, training_type)
                VALUES (?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, user.getId());
            statement.setString(2, trainingType);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Не удалось добавить тип тренировки");
        }

    }

    /**
     * Получает список тренировок по умолчанию.
     *
     * @throws SQLException если возникает ошибка при доступе к базе данных
     */
    private List<String> getDefaultTrainingTypes(User user) throws SQLException {
        List<String> defaultTrainingTypes = getDefaultTrainingTypeList();
        String sql = """
             INSERT INTO permissions.user_training_types (user_id, training_type)
             VALUES (?, ?)
             """;
        try (PreparedStatement insertStatement = connection.prepareStatement(sql)) {
            for (String trainingType : defaultTrainingTypes) {
                insertStatement.setLong(1, user.getId());
                insertStatement.setString(2, trainingType);
                insertStatement.executeUpdate();
            }
        }
        return defaultTrainingTypes;
    }

    // Метод для получения списка типов тренировок по умолчанию
    private List<String> getDefaultTrainingTypeList() {
        return List.of("Кардио", "Силовая", "Плавание"); // Пример типов тренировок по умолчанию
    }

}

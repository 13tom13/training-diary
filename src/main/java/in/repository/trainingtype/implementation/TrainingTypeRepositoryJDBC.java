package in.repository.trainingtype.implementation;

import entity.model.User;
import in.repository.trainingtype.TrainingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация интерфейса {@link TrainingTypeRepository} для хранения типов тренировок в базе данных.
 */
@Repository
@RequiredArgsConstructor
public class TrainingTypeRepositoryJDBC implements TrainingTypeRepository {

    private final DataSource dataSource;

    private Connection getConnection()  {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        Connection connection = getConnection();
        List<String> userTrainingTypes = new ArrayList<>();
        String sql = """
                SELECT training_type
                FROM relations.user_training_types
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
        Connection connection = getConnection();
        String sql = """
                INSERT INTO relations.user_training_types (user_id, training_type)
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
     */
        private List<String> getDefaultTrainingTypes(User user) {
            Connection connection = getConnection();
            System.out.println(user);
            List<String> defaultTrainingTypes = getDefaultTrainingTypeList();
            String sql = """
                 INSERT INTO relations.user_training_types (user_id, training_type)
                 VALUES (?, ?)
                 """;
            try (PreparedStatement insertStatement = connection.prepareStatement(sql)) {
                for (String trainingType : defaultTrainingTypes) {
                    insertStatement.setLong(1, user.getId());
                    insertStatement.setString(2, trainingType);
                    insertStatement.executeUpdate();
                }
            } catch (SQLException e) {
                System.err.println("Не удалось добавить тип тренировки");
            }
            return defaultTrainingTypes;
        }

    // Метод для получения списка типов тренировок по умолчанию
    private List<String> getDefaultTrainingTypeList() {
        return List.of("Кардио", "Силовая", "Плавание"); // Пример типов тренировок по умолчанию
    }

}

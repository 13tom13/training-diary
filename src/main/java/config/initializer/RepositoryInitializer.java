package config.initializer;

import exceptions.RepositoryException;
import in.repository.training.implementation.TrainingRepositoryCollections;
import in.repository.training.implementation.TrainingRepositoryJDBC;
import in.repository.user.implementation.UserRepositoryJDBC;
import model.Training;
import model.User;
import in.repository.training.TrainingRepository;
import in.repository.trainingtype.TrainingTypeRepository;
import in.repository.user.UserRepository;
import in.repository.trainingtype.implementation.TrainingTypeRepositoryImpl;

import java.sql.SQLException;

import static database.DatabaseConnection.getConnection;

/**
 * Класс RepositoryInitializer отвечает за инициализацию репозиториев,
 * используемых в приложении.
 */
public class RepositoryInitializer {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;

    /**
     * Конструирует новый объект RepositoryInitializer и инициализирует
     * репозитории пользователей, тренировок и типов тренировок.
     */
    public RepositoryInitializer() {
        try {
            this.userRepository = new UserRepositoryJDBC(getConnection());
            this.trainingRepository = new TrainingRepositoryJDBC(getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.trainingTypeRepository = new TrainingTypeRepositoryImpl();

    }

    /**
     * Получает репозиторий пользователей.
     *
     * @return Репозиторий пользователей.
     */
    public UserRepository getUserRepository() {
        return userRepository;
    }

    /**
     * Получает репозиторий тренировок.
     *
     * @return Репозиторий тренировок.
     */
    public TrainingRepository getTrainingRepository() {
        return trainingRepository;
    }

    /**
     * Получает репозиторий типов тренировок.
     *
     * @return Репозиторий типов тренировок.
     */
    public TrainingTypeRepository getTrainingTypeRepository() {
        return trainingTypeRepository;
    }

}


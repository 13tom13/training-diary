package config.initializer;

import in.repository.training.implementation.TrainingRepositoryCollections;
import in.repository.training.implementation.TrainingRepositoryJDBC;
import in.repository.trainingtype.implementation.TrainingTypeRepositoryCollections;
import in.repository.trainingtype.implementation.TrainingTypeRepositoryJDBC;
import in.repository.user.implementation.UserRepositoryCollections;
import in.repository.user.implementation.UserRepositoryJDBC;
import in.repository.training.TrainingRepository;
import in.repository.trainingtype.TrainingTypeRepository;
import in.repository.user.UserRepository;

import java.sql.SQLException;

import static config.ApplicationConfig.*;
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

        switch (getApplicationProfile()) {
            case "JDBC":
                try {
                    this.userRepository = new UserRepositoryJDBC(getConnection());
                    this.trainingRepository = new TrainingRepositoryJDBC(getConnection());
                    this.trainingTypeRepository = new TrainingTypeRepositoryJDBC(getConnection());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                liquibaseMigrations();
                break;

            case "Collections":
                this.trainingRepository = new TrainingRepositoryCollections();
                this.userRepository = new UserRepositoryCollections();
                this.trainingTypeRepository = new TrainingTypeRepositoryCollections();
                collectionsMigrations(userRepository, trainingRepository);
                break;

            default:
                try {
                    this.userRepository = new UserRepositoryJDBC(getConnection());
                    this.trainingRepository = new TrainingRepositoryJDBC(getConnection());
                    this.trainingTypeRepository = new TrainingTypeRepositoryJDBC(getConnection());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
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


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
 * Класс RepositoryFactory отвечает за инициализацию репозиториев,
 * используемых в приложении.
 */
public class RepositoryFactory {

    private static UserRepository userRepository;
    private static TrainingRepository trainingRepository;
    private static TrainingTypeRepository trainingTypeRepository;

    /**
     * Приватный конструктор, чтобы предотвратить создание экземпляров класса.
     */
    private RepositoryFactory() {
        // пустое тело конструктора
    }

    /**
     * Инициализирует репозитории для работы с базой данных JDBC.
     */
    private static void initializeJDBCRepositories() {
        try {
            userRepository = new UserRepositoryJDBC(getConnection());
            trainingRepository = new TrainingRepositoryJDBC(getConnection());
            trainingTypeRepository = new TrainingTypeRepositoryJDBC(getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        liquibaseMigrations();
    }

    /**
     * Инициализирует репозитории для работы с коллекциями в памяти.
     */
    private static void initializeCollectionsRepositories() {
        trainingRepository = new TrainingRepositoryCollections();
        userRepository = new UserRepositoryCollections();
        trainingTypeRepository = new TrainingTypeRepositoryCollections();
        collectionsMigrations(userRepository, trainingRepository);
    }

    /**
     * Получает репозиторий пользователей.
     *
     * @return Репозиторий пользователей.
     */
    public static UserRepository getUserRepository() {
        if (userRepository == null) initialize();
        return userRepository;
    }

    /**
     * Получает репозиторий тренировок.
     *
     * @return Репозиторий тренировок.
     */
    public static TrainingRepository getTrainingRepository() {
        if (trainingRepository == null) {
            initialize();
        }
        return trainingRepository;
    }

    /**
     * Получает репозиторий типов тренировок.
     *
     * @return Репозиторий типов тренировок.
     */
    public static TrainingTypeRepository getTrainingTypeRepository() {
        if (trainingTypeRepository == null) {
            initialize();
        }
        return trainingTypeRepository;
    }

    /**
     * Инициализирует репозитории в зависимости от указанного профиля приложения.
     */
    public static void initialize() {
        String applicationProfile = getApplicationProfile();
        switch (applicationProfile) {
            case "JDBC" -> initializeJDBCRepositories();
            case "Collections" -> initializeCollectionsRepositories();
            default -> throw new IllegalArgumentException("Unsupported application profile: " + applicationProfile);
        }
    }
}

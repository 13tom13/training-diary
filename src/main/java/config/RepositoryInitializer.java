package config;

import exceptions.RepositoryException;
import model.Training;
import model.User;
import in.repository.TrainingRepository;
import in.repository.TrainingTypeRepository;
import in.repository.UserRepository;
import in.repository.implementation.TrainingRepositoryImpl;
import in.repository.implementation.TrainingTypeRepositoryImpl;
import in.repository.implementation.UserRepositoryImpl;

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
        this.userRepository = new UserRepositoryImpl();
        this.trainingRepository = new TrainingRepositoryImpl();
        this.trainingTypeRepository = new TrainingTypeRepositoryImpl();

        addAdmin();
        addTestUser();
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

    /**
     * Добавляет администратора в репозиторий пользователей.
     */
    private void addAdmin() {
        System.out.println("Добавление администратора Email: admin, Password: admin");
        User user = new User("Admin", "Admin", "admin", "admin");
        user.setRoles("ADMIN");
        try {
            userRepository.saveUser(user);
        } catch (RepositoryException e) {
            System.err.println("Не удалось добавить администратора");
        }
    }

    /**
     * Добавляет тестового пользователя в репозиторий пользователей
     * и создает для него тестовые тренировки.
     */
    private void addTestUser() {
        System.out.println("Добавление тестового пользователя Email: test@mail.ru, Password: pass");
        User user = new User("Ivan", "Petrov", "test@mail.ru", "pass");
        try {
            userRepository.saveUser(user);
        } catch (RepositoryException e) {
            System.err.println("Не удалось добавить тестового пользователя");
        }
        addTestTrainings(user.getEmail());
    }

    /**
     * Добавляет тестовые тренировки для указанного пользователя.
     *
     * @param testUserEmail Email тестового пользователя.
     */
    private void addTestTrainings(String testUserEmail) {
        Training training1 = new Training("Кардио", "13.04.24", 90, 560);
        Training training2 = new Training("Силовая", "14.04.24", 60, 450);
        Training training3 = new Training("Гибкость", "10.04.24", 45, 300);

        try {
            trainingRepository.saveTraining(testUserEmail, training1);
            trainingRepository.saveTraining(testUserEmail, training2);
            trainingRepository.saveTraining(testUserEmail, training3);
        } catch (RepositoryException e) {
            System.err.println("Не удалось добавить тестовые тренировки");
        }
    }
}


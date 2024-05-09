package config.database;

import entity.model.Roles;
import entity.model.Training;
import entity.model.User;
import exceptions.RepositoryException;
import in.repository.training.TrainingRepository;
import in.repository.user.UserRepository;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * Класс для инициализации коллекций тестовыми данными.
 */
public class CollectionsMigrations {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;

    /**
     * Создает новый экземпляр класса CollectionsMigrations.
     *
     * @param userRepository     репозиторий пользователей
     * @param trainingRepository репозиторий тренировок
     */
    public CollectionsMigrations(UserRepository userRepository, TrainingRepository trainingRepository) {
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
    }

    public void runMigrations() {
        addAdmin();
        addTestUser();
    }

    /**
     * Добавляет администратора в репозиторий пользователей.
     */
    private void addAdmin() {
        System.out.println("Добавление администратора Email: admin, Password: admin");
        User user = new User("Admin", "Admin", "admin", "admin");
        user.getRoles().add(new Roles(null, "ADMIN"));
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
            addTestTrainings(user);
        } catch (RepositoryException e) {
            System.err.println("Не удалось добавить тестового пользователя");
        }
    }

    /**
     * Добавляет тестовые тренировки для указанного пользователя.
     *
     * @param user пользователь, для которого добавляются тренировки
     */
    private void addTestTrainings(User user) {
        HashMap<String, String> additions = new HashMap<>();
        additions.put("comment", "Это дополнительная информация о тренировке");

        Training training1 = new Training("Кардио", LocalDate.of(2024, 4, 15), 90, 560, additions);
        Training training2 = new Training("Силовая", LocalDate.of(2024, 4, 13), 60, 450, additions);
        Training training3 = new Training("Гибкость", LocalDate.of(2024, 4, 10), 45, 300, additions);


        try {
            trainingRepository.saveTraining(user, training1);
            trainingRepository.saveTraining(user, training2);
            trainingRepository.saveTraining(user, training3);
        } catch (RepositoryException e) {
            System.err.println("Не удалось добавить тестовые тренировки");
        }
    }
}

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

public class RepositoryInitializer {

    private final UserRepository userRepository;

    private final TrainingRepository trainingRepository;

    private final TrainingTypeRepository trainingTypeRepository;

    public RepositoryInitializer() {
        this.userRepository = new UserRepositoryImpl();
        this.trainingRepository = new TrainingRepositoryImpl();
        this.trainingTypeRepository = new TrainingTypeRepositoryImpl();

        addAdmin();
        addTestUser();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public TrainingRepository getTrainingRepository() {
        return trainingRepository;
    }


    public TrainingTypeRepository getTrainingTypeRepository() {
        return trainingTypeRepository;
    }

    private void addAdmin() {
        System.out.println("Добавление администратора Email: admin, Password: admin");
        User user = new User("Admin", "Admin", "admin", "admin");
        user.setRoles("ADMIN");
        try {
            userRepository.saveUser(user);
        } catch (RepositoryException e) {
            System.err.println("не удалось добавить администратора");
        }
    }

    private void addTestUser() {
        System.out.println("Добавление тестового пользователя Email: test@mail.ru, Password: pass");
        User user = new User("Ivan", "Petrov", "test@mail.ru", "pass");
        try {
            userRepository.saveUser(user);
        } catch (RepositoryException e) {
            System.err.println("не удалось добавить тестового пользователя");
        }
        addTestTrainings(user.getEmail());
    }

    private void addTestTrainings(String testUserEmail) {
        Training training1 = new Training("Кардио", "13.04.24", 90, 560);
        Training training2 = new Training("Силовая", "14.04.24", 60, 450);
        Training training3 = new Training("Гибкость", "10.04.24", 45, 300);

        try {
            trainingRepository.saveTraining(testUserEmail, training1);
            trainingRepository.saveTraining(testUserEmail, training2);
            trainingRepository.saveTraining(testUserEmail, training3);
        } catch (RepositoryException e) {
            System.err.println("не удалось добавить тестовые тренировки");
        }
    }
}

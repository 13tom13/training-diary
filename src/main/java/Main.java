import in.controller.AdminController;
import in.controller.implementation.AdminControllerImpl;
import in.controller.AuthorizationController;
import in.controller.TrainingStatisticsController;
import in.controller.implementation.TrainingControllerImpl;
import in.controller.UserController;
import in.controller.implementation.TrainingStatisticsControllerImpl;
import in.exception.RepositoryException;
import in.model.Training;
import in.model.User;
import in.service.implementation.TrainingStatisticsService;
import out.TrainingDiary;
import in.repository.TrainingRepository;
import in.repository.UserRepository;
import in.repository.implementation.TrainingRepositoryImpl;
import in.repository.implementation.UserRepositoryImpl;
import in.service.AuthorizationService;
import in.service.TrainingService;
import in.service.UserService;
import in.service.implementation.AuthorizationServiceImpl;
import in.service.implementation.TrainingServiceImpl;
import in.service.implementation.UserServiceImpl;

public class Main {

    public static void main(String[] args) {

        TrainingDiary trainingDiary = getTrainingDiary();

        trainingDiary.start();
        //TODO: Аудит действий пользователя
        // (авторизация, завершение работы, добавление,
        // редактирование и удаление тренировок, получение статистики тренировок и тд)
        //TODO: реализовать администратора

    }

    private static TrainingDiary getTrainingDiary() {
        UserRepository userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);
        UserController userController = new UserController(userService);

        AuthorizationService authorizationService = new AuthorizationServiceImpl(userRepository);
        AuthorizationController authorizationController = new AuthorizationController(authorizationService);

        TrainingRepository trainingRepository = new TrainingRepositoryImpl();
        TrainingService trainingService = new TrainingServiceImpl(trainingRepository);
        TrainingControllerImpl trainingController = new TrainingControllerImpl(trainingService);

        TrainingStatisticsService trainingStatisticsService = new TrainingStatisticsService(trainingService);
        TrainingStatisticsController trainingStatisticsController = new TrainingStatisticsControllerImpl(trainingStatisticsService);

        AdminController adminController = new AdminControllerImpl(userRepository);

        addAdmin(userRepository);
        addTestUser(userRepository, trainingRepository);

        return new TrainingDiary(authorizationController, adminController, userController, trainingController, trainingStatisticsController);
    }

    private static void addAdmin (UserRepository userRepository){
        System.out.println("Add admin  Email: admin, Password: admin");
        User user = new User("Admin", "Admin", "admin", "admin");
        user.setRoles("ADMIN");
        try {
            userRepository.saveUser(user);
        } catch (RepositoryException e) {
            System.err.println("не удалось добавить тестового пользователя");
        }
    }

    private static void addTestUser (UserRepository userRepository, TrainingRepository trainingRepository){
        System.out.println("Add test User  Email: test@mail.ru, Password: pass");
        User user = new User("Ivan", "Petrov", "test@mail.ru", "pass");
        try {
            userRepository.saveUser(user);
        } catch (RepositoryException e) {
            System.err.println("не удалось добавить тестового пользователя");
        }
        addTestTrainings(user.getEmail(), trainingRepository);
    }

    private static void addTestTrainings (String testUserEmail, TrainingRepository trainingRepository){
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

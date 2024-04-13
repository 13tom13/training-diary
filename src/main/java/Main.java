import in.controller.AuthorizationController;
import in.controller.training.implementation.TrainingControllerImpl;
import in.controller.UserController;
import in.exception.RepositoryException;
import in.model.Training;
import in.model.User;
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
        //TODO: реализовать возможность удалять свои тренировки, если это необходимо
        //TODO: реализовать контроль прав пользователя
        //TODO: реализовать возможность получения статистики по тренировкам
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

        addTestUser(userRepository, trainingRepository);

        return new TrainingDiary(authorizationController, userController, trainingController);
    }

    private static void addTestUser (UserRepository userRepository, TrainingRepository trainingRepository){
        System.out.println("Add test User\nEmail: test@mail.ru, Password: pass");
        System.out.println();
        System.out.println();
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
        Training training3 = new Training("Гибкость", "13.04.24", 45, 300);

        System.out.println(training1);

        try {
            trainingRepository.saveTraining(testUserEmail, training1);
            trainingRepository.saveTraining(testUserEmail, training2);
            trainingRepository.saveTraining(testUserEmail, training3);

            trainingRepository.addTrainingAdditional(training1,"Место", "лес");
            trainingRepository.addTrainingAdditional(training1,"Обувь", "кроссы");

            trainingRepository.addTrainingAdditional(training2,"Группа мышц", "плечи");
            trainingRepository.addTrainingAdditional(training2,"Воды выпито", "1.5л");

            trainingRepository.addTrainingAdditional(training3,"Растяжка", "10 см");
            trainingRepository.addTrainingAdditional(training3,"Планка", "2 мин");

        } catch (RepositoryException e) {
            System.err.println("не удалось добавить тестовые тренировки");
        }
    }

}

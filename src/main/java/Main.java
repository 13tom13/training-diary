import controller.AuthorizationController;
import controller.training.implementation.TrainingControllerImpl;
import controller.UserController;
import repository.TrainingRepository;
import repository.UserRepository;
import repository.implementation.TrainingRepositoryImpl;
import repository.implementation.UserRepositoryImpl;
import service.AuthorizationService;
import service.TrainingService;
import service.UserService;
import service.implementation.AuthorizationServiceImpl;
import service.implementation.TrainingServiceImpl;
import service.implementation.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);
        UserController userController = new UserController(userService);
        AuthorizationService authorizationService = new AuthorizationServiceImpl(userRepository);
        AuthorizationController authorizationController = new AuthorizationController(authorizationService);
        TrainingRepository trainingRepository = new TrainingRepositoryImpl();
        TrainingService trainingService = new TrainingServiceImpl(trainingRepository);
        TrainingControllerImpl trainingController = new TrainingControllerImpl(trainingService);

        TrainingDiary trainingDiary = new TrainingDiary(authorizationController, userController, trainingController);
        trainingDiary.start();

    }
}

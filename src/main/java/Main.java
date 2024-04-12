import in.controller.AuthorizationController;
import in.controller.training.implementation.TrainingControllerImpl;
import in.controller.UserController;
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
        //TODO: реализовать возможность удалять свои тренировки, если это необходимо
        //TODO: реализовать контроль прав пользователя
        //TODO: реализовать возможность получения статистики по тренировкам
        //TODO: Аудит действий пользователя
        // (авторизация, завершение работы, добавление,
        // редактирование и удаление тренировок, получение статистики тренировок и тд)
        //TODO: реализовать администратора

    }
}

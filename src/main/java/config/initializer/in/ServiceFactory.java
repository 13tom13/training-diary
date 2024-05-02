package config.initializer.in;

import in.repository.training.TrainingRepository;
import in.repository.trainingtype.TrainingTypeRepository;
import in.repository.user.UserRepository;
import in.service.users.AuthorizationService;
import in.service.training.TrainingService;
import in.service.users.UserService;
import in.service.users.implementation.AuthorizationServiceImpl;
import in.service.training.implementation.TrainingServiceImpl;
import in.service.training.implementation.TrainingStatisticsServiceImp;
import in.service.users.implementation.UserServiceImpl;

import static config.initializer.in.RepositoryFactory.*;

/**
 * Фабричный класс для инициализации сервисов и их зависимостей.
 */
public class ServiceFactory {

    private static final AuthorizationService authorizationService;
    private static final UserService userService;
    private static final TrainingService trainingService;
    private static final TrainingStatisticsServiceImp trainingStatisticsService;

    static {
        // Инициализация репозиториев и сервисов
        UserRepository userRepository = getUserRepository();
        TrainingRepository trainingRepository = getTrainingRepository();
        TrainingTypeRepository trainingTypeRepository = getTrainingTypeRepository();
        authorizationService = new AuthorizationServiceImpl(userRepository);
        userService = new UserServiceImpl(userRepository);
        trainingService = new TrainingServiceImpl(trainingRepository, trainingTypeRepository);
        trainingStatisticsService = new TrainingStatisticsServiceImp(trainingService);
    }


    /**
     * Получает сервис авторизации.
     * @return Объект типа AuthorizationService.
     */
    public static AuthorizationService getAuthorizationService() {
        return authorizationService;
    }

    /**
     * Получает сервис пользователей.
     * @return Объект типа UserService.
     */
    public static UserService getUserService() {
        return userService;
    }

    /**
     * Получает сервис тренировок.
     * @return Объект типа TrainingService.
     */
    public static TrainingService getTrainingService() {
        return trainingService;
    }

    /**
     * Получает сервис статистики тренировок.
     * @return Объект типа TrainingStatisticsServiceImp.
     */
    public static TrainingStatisticsServiceImp getTrainingStatisticsService() {
        return trainingStatisticsService;
    }
}

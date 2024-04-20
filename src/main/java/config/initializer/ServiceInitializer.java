package config.initializer;

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

/**
 * Класс ServiceInitializer отвечает за инициализацию сервисов и их зависимостей.
 */
public class ServiceInitializer {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;

    /**
     * Конструктор класса ServiceInitializer. Инициализирует репозитории, необходимые для сервисов.
     */
    public ServiceInitializer() {
        RepositoryInitializer repositoryInitializer = new RepositoryInitializer();
        userRepository = repositoryInitializer.getUserRepository();
        trainingRepository = repositoryInitializer.getTrainingRepository();
        trainingTypeRepository = repositoryInitializer.getTrainingTypeRepository();
    }

    /**
     * Метод для получения сервиса авторизации.
     * @return Объект типа AuthorizationService.
     */
    public AuthorizationService getAuthorizationService() {
        return new AuthorizationServiceImpl(userRepository);
    }

    /**
     * Метод для получения сервиса пользователей.
     * @return Объект типа UserService.
     */
    public UserService getUserService() {
        return new UserServiceImpl(userRepository);
    }

    /**
     * Метод для получения сервиса тренировок.
     * @return Объект типа TrainingService.
     */
    public TrainingService getTrainingService() {
        return new TrainingServiceImpl(trainingRepository, trainingTypeRepository);
    }

    /**
     * Метод для получения сервиса статистики тренировок.
     * @return Объект типа TrainingStatisticsServiceImp.
     */
    public TrainingStatisticsServiceImp getTrainingStatisticsService() {
        return new TrainingStatisticsServiceImp(getTrainingService());
    }

    /**
     * Метод для получения репозитория пользователей.
     * @return Объект типа UserRepository.
     */
    public UserRepository getUserRepository() {
        return userRepository;
    }

}


package config;

import in.repository.TrainingRepository;
import in.repository.TrainingTypeRepository;
import in.repository.UserRepository;
import in.service.users.AuthorizationService;
import in.service.training.TrainingService;
import in.service.users.UserService;
import in.service.users.implementation.AuthorizationServiceImpl;
import in.service.training.implementation.TrainingServiceImpl;
import in.service.training.implementation.TrainingStatisticsServiceImp;
import in.service.users.implementation.UserServiceImpl;

public class ServiceInitializer {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;

    private final TrainingTypeRepository trainingTypeRepository;

    public ServiceInitializer() {
        RepositoryInitializer repositoryInitializer = new RepositoryInitializer();
        userRepository = repositoryInitializer.getUserRepository();
        trainingRepository = repositoryInitializer.getTrainingRepository();
        trainingTypeRepository = repositoryInitializer.getTrainingTypeRepository();
    }

    public AuthorizationService getAuthorizationService() {
        return new AuthorizationServiceImpl(userRepository);
    }

    public UserService getUserService() {
        return new UserServiceImpl(userRepository);
    }

    public TrainingService getTrainingService() {
        return new TrainingServiceImpl(trainingRepository, trainingTypeRepository);
    }

    public TrainingStatisticsServiceImp getTrainingStatisticsService() {
        return new TrainingStatisticsServiceImp(getTrainingService());
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

}

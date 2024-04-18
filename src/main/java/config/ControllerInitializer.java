package config;

import in.controller.authorization.AuthorizationController;
import in.controller.authorization.implementation.AuthorizationControllerImpl;
import in.controller.training.TrainingController;
import in.controller.training.TrainingStatisticsController;
import in.controller.training.implementation.TrainingControllerImpl;
import in.controller.training.implementation.TrainingStatisticsControllerImpl;
import in.controller.users.AdminController;
import in.controller.users.UserController;
import in.controller.users.implementation.AdminControllerImpl;
import in.controller.users.implementation.UserControllerImpl;

public class ControllerInitializer {

    private final ServiceInitializer serviceInitializer;

    public ControllerInitializer() {
        serviceInitializer = new ServiceInitializer();
    }

    public UserController getUserController() {
        return new UserControllerImpl(serviceInitializer.getUserService());
    }

    public AuthorizationController getAuthorizationController() {
        return new AuthorizationControllerImpl(serviceInitializer.getAuthorizationService());
    }

    public TrainingController getTrainingController() {
        return new TrainingControllerImpl(serviceInitializer.getTrainingService());
    }

    public TrainingStatisticsController getTrainingStatisticsController() {
        return new TrainingStatisticsControllerImpl(serviceInitializer.getTrainingStatisticsService());
    }

    public AdminController getAdminController() {
        return new AdminControllerImpl(serviceInitializer.getUserRepository());
    }

}

package config.initializer;

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

/**
 * Класс ControllerInitializer отвечает за инициализацию различных контроллеров,
 * используемых в приложении.
 */
public class ControllerInitializer {

    private final ServiceInitializer serviceInitializer;

    /**
     * Конструирует новый объект ControllerInitializer.
     */
    public ControllerInitializer() {
        serviceInitializer = new ServiceInitializer();
    }

    /**
     * Получает экземпляр UserController.
     *
     * @return Экземпляр UserController.
     */
    public UserController getUserController() {
        return new UserControllerImpl(serviceInitializer.getUserService());
    }

    /**
     * Получает экземпляр AuthorizationController.
     *
     * @return Экземпляр AuthorizationController.
     */
    public AuthorizationController getAuthorizationController() {
        return new AuthorizationControllerImpl(serviceInitializer.getAuthorizationService());
    }

    /**
     * Получает экземпляр TrainingController.
     *
     * @return Экземпляр TrainingController.
     */
    public TrainingController getTrainingController() {
        return new TrainingControllerImpl(serviceInitializer.getTrainingService());
    }

    /**
     * Получает экземпляр TrainingStatisticsController.
     *
     * @return Экземпляр TrainingStatisticsController.
     */
    public TrainingStatisticsController getTrainingStatisticsController() {
        return new TrainingStatisticsControllerImpl(serviceInitializer.getTrainingStatisticsService());
    }

    /**
     * Получает экземпляр AdminController.
     *
     * @return Экземпляр AdminController.
     */
    public AdminController getAdminController() {
        return new AdminControllerImpl(serviceInitializer.getUserRepository());
    }
}


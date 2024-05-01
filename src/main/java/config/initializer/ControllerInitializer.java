package config.initializer;

import in.controller.authorization.AuthorizationController;
import in.controller.authorization.implementation.AuthorizationControllerConsole;
import in.controller.training.TrainingController;
import in.controller.training.TrainingStatisticsController;
import in.controller.training.implementation.TrainingControllerConsole;
import in.controller.training.implementation.TrainingStatisticsControllerConsole;
import in.controller.users.AdminController;
import in.controller.users.UserController;
import in.controller.users.implementation.AdminControllerConsole;
import in.controller.users.implementation.UserControllerConsole;

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
        return new UserControllerConsole(serviceInitializer.getUserService());
    }

    /**
     * Получает экземпляр AuthorizationController.
     *
     * @return Экземпляр AuthorizationController.
     */
    public AuthorizationController getAuthorizationController() {
        return new AuthorizationControllerConsole(serviceInitializer.getAuthorizationService());
    }

    /**
     * Получает экземпляр TrainingController.
     *
     * @return Экземпляр TrainingController.
     */
    public TrainingController getTrainingController() {
        return new TrainingControllerConsole(serviceInitializer.getTrainingService());
    }

    /**
     * Получает экземпляр TrainingStatisticsController.
     *
     * @return Экземпляр TrainingStatisticsController.
     */
    public TrainingStatisticsController getTrainingStatisticsController() {
        return new TrainingStatisticsControllerConsole(serviceInitializer.getTrainingStatisticsService());
    }

    /**
     * Получает экземпляр AdminController.
     *
     * @return Экземпляр AdminController.
     */
    public AdminController getAdminController() {
        return new AdminControllerConsole(serviceInitializer.getUserRepository());
    }
}


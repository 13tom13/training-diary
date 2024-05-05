package config.initializer;

import in.controller.authorization.AuthorizationController;
import in.controller.authorization.implementation.AuthorizationControllerHTTP;
import in.controller.training.TrainingController;
import in.controller.training.statistics.TrainingStatisticsController;
import in.controller.training.implementation.TrainingControllerHTTP;
import in.controller.training.statistics.implementation.TrainingStatisticsControllerHTTP;
import in.controller.users.AdminController;
import in.controller.users.UserController;
import in.controller.users.implementation.AdminControllerConsole;
import in.controller.users.implementation.AdminControllerHTTP;
import in.controller.users.implementation.UserControllerHTTP;

import static config.initializer.RepositoryFactory.getUserRepository;

/**
 * Фабричный класс для инициализации контроллеров приложения.
 */
public class ControllerFactory {

    private static final ControllerFactory instance;

    private final UserController userController;
    private final AuthorizationController authorizationController;
    private final TrainingController trainingController;
    private final TrainingStatisticsController trainingStatisticsController;
    private final AdminController adminController;

    static {
        instance = new ControllerFactory();
    }

    private ControllerFactory() {
//        userController = new UserControllerConsole(getUserService());
//        authorizationController = new AuthorizationControllerConsole(getAuthorizationService());
//        trainingController = new TrainingControllerConsole(getTrainingService());
//        trainingStatisticsController = new TrainingStatisticsControllerConsole(ServiceFactory.getTrainingStatisticsService());
//        adminController = new AdminControllerConsole(getUserRepository());
        userController = new UserControllerHTTP();
        authorizationController = new AuthorizationControllerHTTP();
        trainingController = new TrainingControllerHTTP();
        trainingStatisticsController = new TrainingStatisticsControllerHTTP();
        adminController = new AdminControllerHTTP();
    }

    /**
     * Получает экземпляр фабрики контроллеров.
     *
     * @return Экземпляр фабрики контроллеров.
     */
    public static ControllerFactory getInstance() {
        return instance;
    }

    /**
     * Получает экземпляр UserController.
     *
     * @return Экземпляр UserController.
     */
    public UserController getUserController() {
        return userController;
    }

    /**
     * Получает экземпляр AuthorizationController.
     *
     * @return Экземпляр AuthorizationController.
     */
    public AuthorizationController getAuthorizationController() {
        return authorizationController;
    }

    /**
     * Получает экземпляр TrainingController.
     *
     * @return Экземпляр TrainingController.
     */
    public TrainingController getTrainingController() {
        return trainingController;
    }

    /**
     * Получает экземпляр TrainingStatisticsController.
     *
     * @return Экземпляр TrainingStatisticsController.
     */
    public TrainingStatisticsController getTrainingStatisticsController() {
        return trainingStatisticsController;
    }

    /**
     * Получает экземпляр AdminController.
     *
     * @return Экземпляр AdminController.
     */
    public AdminController getAdminController() {
        return adminController;
    }
}

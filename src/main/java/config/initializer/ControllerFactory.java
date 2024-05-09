package config.initializer;

import in.controller.authorization.AuthorizationController;
import in.controller.authorization.implementation.AuthorizationControllerConsole;
import in.controller.authorization.implementation.AuthorizationControllerHTTP;
import in.controller.training.TrainingController;
import in.controller.training.implementation.TrainingControllerConsole;
import in.controller.training.statistics.TrainingStatisticsController;
import in.controller.training.implementation.TrainingControllerHTTP;
import in.controller.training.statistics.implementation.TrainingStatisticsControllerConsole;
import in.controller.training.statistics.implementation.TrainingStatisticsControllerHTTP;
import in.controller.users.AdminController;
import in.controller.users.UserController;
import in.controller.users.implementation.AdminControllerConsole;
import in.controller.users.implementation.AdminControllerHTTP;
import in.controller.users.implementation.UserControllerConsole;
import in.controller.users.implementation.UserControllerHTTP;
import in.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Фабричный класс для инициализации контроллеров приложения.
 */
@Component
public class ControllerFactory {
//
//    private final UserController userController;
//    private final AuthorizationController authorizationController;
//    private final TrainingController trainingController;
//    private final TrainingStatisticsController trainingStatisticsController;
//    private final AdminController adminController;
//
//    @Autowired
//    public ControllerFactory(UserController userController,
//                             AuthorizationController authorizationController,
//                             TrainingController trainingController,
//                             TrainingStatisticsController trainingStatisticsController,
//                             AdminController adminController) {
//        this.userController = userController;
//        this.authorizationController = authorizationController;
//        this.trainingController = trainingController;
//        this.trainingStatisticsController = trainingStatisticsController;
//        this.adminController = adminController;
//    }
////        userController = new UserControllerHTTP();
////        authorizationController = new AuthorizationControllerHTTP();
////        trainingController = new TrainingControllerHTTP();
////        trainingStatisticsController = new TrainingStatisticsControllerHTTP();
////        adminController = new AdminControllerHTTP();
//    }
//
//
//    /**
//     * Получает экземпляр фабрики контроллеров.
//     *
//     * @return Экземпляр фабрики контроллеров.
//     */
//    public static ControllerFactory getInstance() {
//        return instance;
//    }
//
//    /**
//     * Получает экземпляр UserController.
//     *
//     * @return Экземпляр UserController.
//     */
//    public UserController getUserController() {
//        return userController;
//    }
//
//    /**
//     * Получает экземпляр AuthorizationController.
//     *
//     * @return Экземпляр AuthorizationController.
//     */
//    public AuthorizationController getAuthorizationController() {
//        return authorizationController;
//    }
//
//    /**
//     * Получает экземпляр TrainingController.
//     *
//     * @return Экземпляр TrainingController.
//     */
//    public TrainingController getTrainingController() {
//        return trainingController;
//    }
//
//    /**
//     * Получает экземпляр TrainingStatisticsController.
//     *
//     * @return Экземпляр TrainingStatisticsController.
//     */
//    public TrainingStatisticsController getTrainingStatisticsController() {
//        return trainingStatisticsController;
//    }
//
//    /**
//     * Получает экземпляр AdminController.
//     *
//     * @return Экземпляр AdminController.
//     */
//    public AdminController getAdminController() {
//        return adminController;
//    }
}

package out.menu.authorization;

import in.controller.AdminController;
import in.controller.AuthorizationController;
import in.controller.UserController;
import in.controller.training.TrainingController;
import in.controller.training.TrainingStatisticsController;
import in.exception.security.AuthorizationException;
import in.model.Roles;
import in.model.User;
import out.menu.authorization.account.ViewAdminAccount;
import out.menu.authorization.account.ViewUserAccount;

import java.util.Scanner;

public class ViewAuthorization {

    private final AuthorizationController authorizationController;

    private final AdminController adminController;

    private final UserController userController;

    private final TrainingController trainingController;

    private final TrainingStatisticsController trainingStatisticsController;

    private final Scanner scanner;


    public ViewAuthorization(AuthorizationController authorizationController, AdminController adminController,
                             UserController userController,
                             TrainingController trainingController,
                             TrainingStatisticsController trainingStatisticsController,
                             Scanner scanner) {
        this.authorizationController = authorizationController;
        this.adminController = adminController;
        this.userController = userController;
        this.trainingController = trainingController;
        this.trainingStatisticsController = trainingStatisticsController;
        this.scanner = scanner;
    }

    public void login() {
        System.out.println("Вы выбрали авторизацию");
        System.out.println("Введите email:");
        String authEmail = scanner.nextLine();
        System.out.println("Введите пароль:");
        String authPassword = scanner.nextLine();
        try {
            User user = authorizationController.login(authEmail, authPassword);
            if (user.getRoles().contains(Roles.ADMIN)) {
                ViewAdminAccount viewAdminAccount = new ViewAdminAccount(adminController, user, scanner);
                viewAdminAccount.adminAccountMenu();
            }
            if (user.getRoles().contains(Roles.USER)) {
                ViewUserAccount viewUserAccount = new ViewUserAccount(trainingController, trainingStatisticsController, user, scanner);
                viewUserAccount.userAccountMenu();
            }
        } catch (AuthorizationException e) {
            System.err.println(e.getMessage());
        }

    }

    public void register() {
        System.out.println("Вы выбрали регистрацию");
        System.out.println("Введите email:");
        String regEmail = scanner.nextLine();
        System.out.println("Введите имя:");
        String regFirstName = scanner.nextLine();
        System.out.println("Введите фамилию:");
        String regLastName = scanner.nextLine();
        System.out.println("Введите пароль:");
        String regPassword = scanner.nextLine();
        userController.createNewUser(regFirstName, regLastName, regEmail, regPassword);
    }

}

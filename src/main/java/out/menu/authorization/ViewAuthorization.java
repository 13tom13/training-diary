package out.menu.authorization;

import in.controller.AuthorizationController;
import in.controller.UserController;
import in.controller.training.TrainingController;
import in.exception.AuthorizationException;
import in.exception.ServiceException;
import in.model.User;
import out.menu.authorization.account.ViewUserAccount;

import java.util.Scanner;

public class ViewAuthorization {

    private final AuthorizationController authorizationController;

    private final UserController userController;

    private final TrainingController trainingController;

    private final Scanner scanner;


    public ViewAuthorization(AuthorizationController authorizationController,
                             UserController userController,
                             TrainingController trainingController,
                             Scanner scanner) {
        this.authorizationController = authorizationController;
        this.userController = userController;
        this.trainingController = trainingController;
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
            ViewUserAccount viewUserAccount = new ViewUserAccount(trainingController, user, scanner);
            viewUserAccount.userAccountMenu();
        } catch (AuthorizationException | ServiceException e) {
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

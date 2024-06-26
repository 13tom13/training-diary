package out.menu.authorization;

import exceptions.security.AuthorizationException;
import in.controller.authorization.AuthorizationController;
import in.controller.training.TrainingController;
import in.controller.training.TrainingStatisticsController;
import in.controller.users.AdminController;
import in.controller.users.UserController;
import model.Roles;
import model.User;
import out.menu.account.ViewAdminAccount;
import out.menu.account.ViewUserAccount;

import java.util.Scanner;

/**
 * Класс ViewAuthorization представляет собой меню для авторизации и регистрации пользователей.
 */
public class ViewAuthorization {

    private final AuthorizationController authorizationController;
    private final AdminController adminController;
    private final UserController userController;
    private final TrainingController trainingController;
    private final TrainingStatisticsController trainingStatisticsController;
    private final Scanner scanner;

    /**
     * Конструктор класса ViewAuthorization.
     * @param authorizationController Контроллер авторизации.
     * @param adminController Контроллер администратора.
     * @param userController Контроллер пользователя.
     * @param trainingController Контроллер тренировок.
     * @param trainingStatisticsController Контроллер статистики тренировок.
     * @param scanner Сканер для ввода данных.
     */
    public ViewAuthorization(AuthorizationController authorizationController, AdminController adminController,
                             UserController userController, TrainingController trainingController,
                             TrainingStatisticsController trainingStatisticsController, Scanner scanner) {
        this.authorizationController = authorizationController;
        this.adminController = adminController;
        this.userController = userController;
        this.trainingController = trainingController;
        this.trainingStatisticsController = trainingStatisticsController;
        this.scanner = scanner;
    }

    /**
     * Метод для авторизации пользователя.
     */
    public void login() {
        System.out.println("Вы выбрали авторизацию");
        System.out.println("Введите email:");
        String authEmail = scanner.nextLine();
        System.out.println("Введите пароль:");
        String authPassword = scanner.nextLine();
        try {
            User user = authorizationController.login(authEmail, authPassword);
            if (user.getRoles().contains(Roles.ADMIN)) {
                ViewAdminAccount viewAdminAccount = new ViewAdminAccount(adminController, trainingController, scanner);
                viewAdminAccount.adminAccountMenu();
            } else if (user.getRoles().contains(Roles.USER)) {
                ViewUserAccount viewUserAccount = new ViewUserAccount(trainingController, trainingStatisticsController, user, scanner);
                viewUserAccount.userAccountMenu();
            }
        } catch (AuthorizationException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Метод для регистрации нового пользователя.
     */
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

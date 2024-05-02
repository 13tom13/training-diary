package out.menu.authorization.implementation;

import config.initializer.in.ControllerFactory;
import entities.dto.AuthorizationDTO;
import entities.dto.RegistrationDTO;
import entities.dto.UserDTO;
import exceptions.security.AuthorizationException;
import in.controller.authorization.AuthorizationController;
import in.controller.users.UserController;
import out.menu.account.ViewAdminAccount;
import out.menu.account.ViewUserAccount;
import out.menu.authorization.ViewAuthorization;

import java.util.Scanner;

import static utils.Utils.hisRole;

/**
 * Класс ViewAuthorizationByController представляет собой меню для авторизации и регистрации пользователей.
 */
public class ViewAuthorizationByController implements ViewAuthorization {

    private final AuthorizationController authorizationController;
    private final UserController userController;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Конструктор класса ViewAuthorizationByController.
     *
     */
    public ViewAuthorizationByController() {
        this.authorizationController = ControllerFactory.getInstance().getAuthorizationController();
        this.userController = ControllerFactory.getInstance().getUserController();
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
            UserDTO userDTO = authorizationController.login(new AuthorizationDTO(authEmail, authPassword));
            if (hisRole(userDTO,"ADMIN")) {
                ViewAdminAccount viewAdminAccount = new ViewAdminAccount();
                viewAdminAccount.adminAccountMenu();
            } else if (hisRole(userDTO,"USER")) {
                ViewUserAccount viewUserAccount = new ViewUserAccount(userDTO);
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
        RegistrationDTO regDTO = new RegistrationDTO(regEmail, regFirstName, regLastName, regPassword);
        userController.createNewUser(regDTO);
    }
}
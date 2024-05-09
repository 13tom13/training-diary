package out.menu.authorization;

import entity.dto.AuthorizationDTO;
import entity.dto.RegistrationDTO;
import entity.dto.UserDTO;
import exceptions.security.AuthorizationException;
import in.controller.authorization.AuthorizationController;
import in.controller.users.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import out.menu.account.ViewAdminAccount;
import out.menu.account.ViewUserAccount;

import java.io.IOException;
import java.util.Scanner;

import static utils.Utils.hisRole;

/**
 * Класс ViewAuthorization представляет собой меню для авторизации и регистрации пользователей.
 */
@Component
public class ViewAuthorization {

    private final AuthorizationController authorizationController;
    private final UserController userController;
    private final Scanner scanner = new Scanner(System.in);
    private ViewAdminAccount viewAdminAccount;
    private ViewUserAccount viewUserAccount;

    /**
     * Конструктор класса ViewAuthorization.
     *
     */
    @Autowired
    public ViewAuthorization(AuthorizationController authorizationController, UserController userController) {

        this.authorizationController = authorizationController;
        this.userController = userController;
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
                viewAdminAccount.adminAccountMenu();
            } else if (hisRole(userDTO,"USER")) {
                viewUserAccount.setUserDTO(userDTO);
                viewUserAccount.userAccountMenu();
            }
        } catch (AuthorizationException | IOException e) {
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
        try {
            userController.createNewUser(regDTO);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
package out.menu.authorization;

import entity.dto.AuthorizationDTO;
import entity.dto.RegistrationDTO;
import entity.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import out.menu.account.ViewAdminAccount;
import out.menu.account.ViewUserAccount;
import out.messenger.AuthorizationHTTPMessenger;
import out.messenger.UserHTTPMessenger;

import java.io.IOException;
import java.util.Scanner;

import static utils.Utils.hisRole;

/**
 * Класс ViewAuthorization представляет собой меню для авторизации и регистрации пользователей.
 */
@Component
public class ViewAuthorization {

    private final AuthorizationHTTPMessenger authorizationHTTPMessenger;
    private final UserHTTPMessenger userHTTPMessenger;

    private final ViewAdminAccount viewAdminAccount;
    private final ViewUserAccount viewUserAccount;

    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public ViewAuthorization(AuthorizationHTTPMessenger authorizationHTTPMessenger, UserHTTPMessenger userHTTPMessenger, ViewAdminAccount viewAdminAccount, ViewUserAccount viewUserAccount) {
        this.authorizationHTTPMessenger = authorizationHTTPMessenger;
        this.userHTTPMessenger = userHTTPMessenger;
        this.viewAdminAccount = viewAdminAccount;
        this.viewUserAccount = viewUserAccount;
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
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(authEmail, authPassword);
        UserDTO userDTO = authorizationHTTPMessenger.login(authorizationDTO);
            if (hisRole(userDTO,"ADMIN")) {
                viewAdminAccount.adminAccountMenu();
            } else if (hisRole(userDTO,"USER")) {
                viewUserAccount.userAccountMenu(userDTO);
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
            userHTTPMessenger.registration(regDTO);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
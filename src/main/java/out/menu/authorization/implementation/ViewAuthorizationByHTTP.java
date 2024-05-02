package out.menu.authorization.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.AuthorizationDTO;
import entities.dto.RegistrationDTO;
import entities.dto.UserDTO;
import out.menu.account.ViewAdminAccount;
import out.menu.account.ViewUserAccount;
import out.menu.authorization.ViewAuthorization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static config.ApplicationConfig.getRootURL;
import static utils.HTTP.sendPostRequest;
import static utils.Utils.hisRole;

/**
 * Класс ViewAuthorizationByController представляет собой меню для авторизации и регистрации пользователей.
 */
public class ViewAuthorizationByHTTP implements ViewAuthorization {

    private final String rootURL = getRootURL();
    private final String loginServletPath = "/login";
    private final String registerServletPath = "/registration";
    private final Scanner scanner = new Scanner(System.in);
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Конструктор класса ViewAuthorizationByController.
     *
     */
    public ViewAuthorizationByHTTP() {
    }

    /**
     * Метод для авторизации пользователя.
     */
    @Override
    public void login()  {
        System.out.println("Вы выбрали авторизацию");
        System.out.println("Введите email:");
        String authEmail = scanner.nextLine();
        System.out.println("Введите пароль:");
        String authPassword = scanner.nextLine();
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(authEmail, authPassword);

        // Отправка POST запроса на сервлет
        String requestURL = rootURL + loginServletPath;
        try {
            // Преобразование объекта AuthorizationDTO в JSON
            String jsonResponse = sendPostRequest(requestURL, authorizationDTO);
            // Преобразование JSON ответа в объект UserDTO
            UserDTO userDTO = objectMapper.readValue(jsonResponse, UserDTO.class);
            if (hisRole(userDTO,"ADMIN")) {
                ViewAdminAccount viewAdminAccount = new ViewAdminAccount();
                viewAdminAccount.adminAccountMenu();
            } else if (hisRole(userDTO,"USER")) {
                ViewUserAccount viewUserAccount = new ViewUserAccount(userDTO);
                viewUserAccount.userAccountMenu();
            }
            // Дальнейшая обработка полученного UserDTO
        } catch (IOException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
        }

    }



    /**
     * Метод для регистрации нового пользователя.
     */
    @Override
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
        String servletUrl = rootURL + registerServletPath;

        try {
            String response = sendPostRequest(servletUrl, regDTO);
            System.out.println("Ответ от сервера: " + response);
        } catch (IOException e) {
            System.err.println("Ошибка при отправке запроса на сервер: " + e.getMessage());
        }
    }


}

package out.menu.authorization.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.AuthorizationDTO;
import entities.dto.RegistrationDTO;
import entities.dto.UserDTO;
import in.controller.authorization.AuthorizationController;
import in.controller.training.TrainingController;
import in.controller.training.TrainingStatisticsController;
import in.controller.users.AdminController;
import in.controller.users.UserController;
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

import static config.ApplicationConfig.getApplicationURL;
import static utils.Utils.hisRole;

/**
 * Класс ViewAuthorizationController представляет собой меню для авторизации и регистрации пользователей.
 */
public class ViewAuthorizationHTTP implements ViewAuthorization {

    private final String url = getApplicationURL();

    private final AdminController adminController;
    private final UserController userController;
    private final TrainingController trainingController;
    private final TrainingStatisticsController trainingStatisticsController;
    private final Scanner scanner;
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Конструктор класса ViewAuthorizationController.
     *
     * @param adminController              Контроллер администратора.
     * @param userController               Контроллер пользователя.
     * @param trainingController           Контроллер тренировок.
     * @param trainingStatisticsController Контроллер статистики тренировок.
     * @param scanner                      Сканер для ввода данных.
     */
    public ViewAuthorizationHTTP(AdminController adminController,
                                 UserController userController, TrainingController trainingController,
                                 TrainingStatisticsController trainingStatisticsController, Scanner scanner) {
        this.adminController = adminController;
        this.userController = userController;
        this.trainingController = trainingController;
        this.trainingStatisticsController = trainingStatisticsController;
        this.scanner = scanner;
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
        String servletUrl = url + "/login";
        try {
            // Преобразование объекта AuthorizationDTO в JSON
            String requestBody = objectMapper.writeValueAsString(authorizationDTO);
            String jsonResponse = sendPostRequest(servletUrl, requestBody);
            // Преобразование JSON ответа в объект UserDTO
            UserDTO userDTO = objectMapper.readValue(jsonResponse, UserDTO.class);
            if (hisRole(userDTO,"ADMIN")) {
                ViewAdminAccount viewAdminAccount = new ViewAdminAccount(adminController, trainingController, scanner);
                viewAdminAccount.adminAccountMenu();
            } else if (hisRole(userDTO,"USER")) {
                ViewUserAccount viewUserAccount = new ViewUserAccount(trainingController, trainingStatisticsController, userDTO, scanner);
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

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(regDTO);
        } catch (IOException e) {
            System.err.println("Ошибка при сериализации объекта в JSON: " + e.getMessage());
            return;
        }

        String servletUrl = url + "/registration";
        try {
            String response = sendPostRequest(servletUrl, requestBody);
            System.out.println("Ответ от сервера: " + response);
        } catch (IOException e) {
            System.err.println("Ошибка при отправке запроса на сервер: " + e.getMessage());
        }
    }

    private String sendPostRequest(String servletUrl, String requestBody) throws IOException {
        URL url = new URL(servletUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }finally {
                connection.disconnect();
            }
        } else {
            throw new IOException("HTTP error code: " + responseCode);
        }
    }

}

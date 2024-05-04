package out.menu.account;

import config.initializer.ControllerFactory;
import entities.dto.UserDTO;
import entities.model.User;
import exceptions.UserNotFoundException;
import in.controller.users.AdminController;
import out.menu.training.ViewTraining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static utils.Logger.LOGS_PATH;

/**
 * Класс ViewUsers представляет меню для просмотра и управления пользователями.
 */
public class ViewUsers {

    private final AdminController adminController;
    private final ViewTraining viewTraining;
    private final ViewUsersEdition viewUsersEdition;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Конструктор класса ViewUsers.
     *
     */
    public ViewUsers() {
        this.adminController = ControllerFactory.getInstance().getAdminController();
        this.viewTraining = new ViewTraining();
        this.viewUsersEdition = new ViewUsersEdition();
    }

    /**
     * Метод для просмотра всех пользователей.
     */
    public void viewAllUsers() {
        System.out.println("Список всех пользователей:");
        List<User> allUsers = adminController.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user);
        }
    }

    /**
     * Метод для просмотра данных конкретного пользователя.
     */
    public void viewUser() {
        System.out.println("Введите email пользователя для редактирования:");
        String email = scanner.nextLine();
        try {
            UserDTO userDTO = adminController.getUser(email);
            userViewMenu(userDTO);
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Метод для отображения меню действий с пользователем.
     *
     * @param userDTO Пользователь, для которого отображается меню.
     */
    public void userViewMenu(UserDTO userDTO) {
        boolean view = true;
        while (view) {
            System.out.println();
            System.out.println(userDTO);
            System.out.println("Выберите действие:");
            System.out.println("1. редактировать пользователя");
            System.out.println("2. просмотр тренировок пользователя");
            System.out.println("3. просмотр логов пользователя");
            System.out.println("4. выход");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> viewUsersEdition.userEdition(userDTO);
                    case 2 -> viewTraining.viewAllTraining(userDTO);
                    case 3 -> viewLogsUser(userDTO);
                    case 4 -> {
                        System.out.println("Выход из просмотра пользователя " + userDTO.getEmail());
                        view = false;
                    }
                    default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
                }
            } else {
                System.out.println("Неверный выбор. Попробуйте еще раз.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Метод для просмотра логов пользователя.
     *
     * @param userDTO Пользователь, чьи логи просматриваются.
     */
    private void viewLogsUser(UserDTO userDTO) {
        String userEmail = userDTO.getEmail();
        String fileName = LOGS_PATH + "/" + userEmail.replace("@", "_") + ".log";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            System.out.println("Логи пользователя " + userEmail + ":");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла логов пользователя " + userEmail + ": " + e.getMessage());
        }
    }
}
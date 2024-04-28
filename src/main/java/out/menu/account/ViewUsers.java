package out.menu.account;

import in.controller.users.AdminController;
import in.controller.training.TrainingController;
import entities.model.User;
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
    private final Scanner scanner;

    /**
     * Конструктор класса ViewUsers.
     *
     * @param adminController    Контроллер администратора.
     * @param trainingController Контроллер тренировок.
     * @param scanner            Сканер для ввода данных.
     */
    public ViewUsers(AdminController adminController, TrainingController trainingController, Scanner scanner) {
        this.adminController = adminController;
        this.viewTraining = new ViewTraining(trainingController);
        this.viewUsersEdition = new ViewUsersEdition(adminController, scanner);
        this.scanner = scanner;
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
        User user = adminController.getUser(email);
        if (user != null) {
            userViewMenu(user);
        } else {
            System.out.println("Пользователь с email " + email + " не найден.");
        }
    }

    /**
     * Метод для отображения меню действий с пользователем.
     *
     * @param user Пользователь, для которого отображается меню.
     */
    public void userViewMenu(User user) {
        boolean view = true;
        while (view) {
            System.out.println();
            System.out.println(user);
            System.out.println("Выберите действие:");
            System.out.println("1. редактировать пользователя");
            System.out.println("2. просмотр тренировок пользователя");
            System.out.println("3. просмотр логов пользователя");
            System.out.println("4. выход");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> viewUsersEdition.userEdition(user);
                    case 2 -> viewTraining.viewAllTraining(user);
                    case 3 -> viewLogsUser(user);
                    case 4 -> {
                        System.out.println("Выход из просмотра пользователя " + user.getEmail());
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
     * @param user Пользователь, чьи логи просматриваются.
     */
    private void viewLogsUser(User user) {
        String userEmail = user.getEmail();
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
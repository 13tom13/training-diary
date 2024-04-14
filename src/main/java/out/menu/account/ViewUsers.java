package out.menu.account;

import in.controller.AdminController;
import in.controller.TrainingController;
import in.model.User;
import out.menu.training.ViewTraining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static in.logger.Logger.LOGS_PATH;

public class ViewUsers {

    private final AdminController adminController;

    private final TrainingController trainingController;

    private final ViewTraining viewTraining;

    private final ViewUsersEdition viewUsersEdition;

    private final Scanner scanner;

    public ViewUsers(AdminController adminController, TrainingController trainingController, Scanner scanner) {
        this.adminController = adminController;
        this.viewTraining = new ViewTraining(trainingController);
        this.trainingController = trainingController;
        this.viewUsersEdition = new ViewUsersEdition(adminController, scanner);
        this.scanner = scanner;
    }


    public void viewAllUsers() {
        System.out.println("Список всех пользователей:");
        List<User> allUsers = adminController.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user);
        }
    }

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
                    case 1:
                        viewUsersEdition.userEdition(user);
                        break;
                    case 2:
                        viewTraining.viewAllTraining(user);
                        break;
                    case 3:
                        viewLogsUser(user);
                        break;
                    case 4:
                        System.out.println("До свидания!");
                        view = false;
                        break;
                    default:
                        System.out.println("Неверный выбор. Попробуйте еще раз.");
                        break;
                }
            } else {
                System.out.println("Неверный выбор. Попробуйте еще раз.");
                scanner.nextLine();
            }
        }

    }

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

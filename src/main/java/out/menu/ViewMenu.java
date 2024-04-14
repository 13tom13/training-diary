package out.menu;

import in.controller.AdminController;
import in.controller.AuthorizationController;
import in.controller.UserController;
import in.controller.training.TrainingController;
import in.controller.training.TrainingStatisticsController;
import out.menu.authorization.ViewAuthorization;

import java.util.Scanner;

public class ViewMenu {

    private final ViewAuthorization viewAuthorization;

    public ViewMenu(AuthorizationController authorizationController,
                    AdminController adminController,
                    UserController userController,
                    TrainingController trainingController,
                    TrainingStatisticsController trainingStatisticsController) {
        Scanner scanner = new Scanner(System.in);
        this.viewAuthorization = new ViewAuthorization(authorizationController, adminController, userController,
                trainingController, trainingStatisticsController, scanner);
    }

    private void viewWelcomeMessage() {
        System.out.println("Добро пожаловать в тренировочный дневник!");
    }

    private void viewMainMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1. Авторизация");
        System.out.println("2. Регистрация");
        System.out.println("3. Выход");
    }

    public void processMainMenuChoice() {
        viewWelcomeMessage();
        Scanner scanner = new Scanner(System.in);
        boolean startMenu = true;
        while (startMenu) {
            viewMainMenu();
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        viewAuthorization.login();
                        break;
                    case 2:
                        viewAuthorization.register();
                        break;
                    case 3:
                        System.out.println("До свидания!");
                        startMenu = false;
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
}

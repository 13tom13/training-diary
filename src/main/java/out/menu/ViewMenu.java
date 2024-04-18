package out.menu;

import in.controller.authorization.AuthorizationController;
import in.controller.training.TrainingController;
import in.controller.training.TrainingStatisticsController;
import in.controller.users.AdminController;
import in.controller.users.UserController;
import out.menu.authorization.ViewAuthorization;

import java.util.Scanner;

/**
 * Класс ViewMenu представляет собой меню приложения для управления авторизацией и регистрацией пользователей.
 */
public class ViewMenu {

    private final ViewAuthorization viewAuthorization;

    /**
     * Конструктор класса ViewMenu.
     * @param authorizationController Контроллер авторизации.
     * @param adminController Контроллер администратора.
     * @param userController Контроллер пользователя.
     * @param trainingController Контроллер тренировок.
     * @param trainingStatisticsController Контроллер статистики тренировок.
     */
    public ViewMenu(AuthorizationController authorizationController,
                    AdminController adminController,
                    UserController userController,
                    TrainingController trainingController,
                    TrainingStatisticsController trainingStatisticsController) {
        Scanner scanner = new Scanner(System.in);
        this.viewAuthorization = new ViewAuthorization(authorizationController, adminController, userController,
                trainingController, trainingStatisticsController, scanner);
    }

    /**
     * Выводит приветственное сообщение при запуске меню.
     */
    private void viewWelcomeMessage() {
        System.out.println();
        System.out.println("Добро пожаловать в тренировочный дневник!");
    }

    /**
     * Выводит главное меню приложения.
     */
    private void viewMainMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1. Авторизация");
        System.out.println("2. Регистрация");
        System.out.println("3. Выход");
    }

    /**
     * Обрабатывает выбор пользователя в главном меню.
     */
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
                    case 1 -> viewAuthorization.login();
                    case 2 -> viewAuthorization.register();
                    case 3 -> {
                        System.out.println("Завершение программы");
                        startMenu = false;
                    }
                    default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
                }
            } else {
                System.out.println("Неверный выбор. Попробуйте еще раз.");
                scanner.nextLine();
            }
        }
    }
}

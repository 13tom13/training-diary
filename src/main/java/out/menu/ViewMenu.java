package out.menu;

import out.menu.authorization.ViewAuthorization;
import out.menu.authorization.implementation.ViewAuthorizationByHTTP;

import java.util.Scanner;

/**
 * Класс ViewMenu представляет собой меню приложения для управления авторизацией и регистрацией пользователей.
 */
public class ViewMenu {

    private final ViewAuthorization viewAuthorization;

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Конструктор класса ViewMenu.
     */
    public ViewMenu() {
//      this.viewAuthorization = new ViewAuthorizationByController();
        this.viewAuthorization = new ViewAuthorizationByHTTP();
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

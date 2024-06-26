package out.menu.account;

import in.controller.users.AdminController;
import in.controller.training.TrainingController;

import java.util.Scanner;

/**
 * Класс ViewAdminAccount представляет меню для администратора.
 */
public class ViewAdminAccount {

    private final ViewUsers viewUsers;
    private final Scanner scanner;

    /**
     * Конструктор класса ViewAdminAccount.
     * @param adminController Контроллер администратора.
     * @param trainingController Контроллер тренировок.
     * @param scanner Сканер для ввода данных.
     */
    public ViewAdminAccount(AdminController adminController, TrainingController trainingController, Scanner scanner) {
        viewUsers = new ViewUsers(adminController, trainingController, scanner);
        this.scanner = scanner;
    }

    /**
     * Метод для отображения меню для администратора.
     */
    public void adminAccountMenu() {
        boolean startAccount = true;
        while (startAccount) {
            System.out.println("\nДобро пожаловать Администратор!");
            System.out.println("Выберите действие:");
            System.out.println("1. Просмотр всех пользователей");
            System.out.println("2. Выбрать пользователя для просмотра");
            System.out.println("3. Выход");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> viewUsers.viewAllUsers();
                    case 2 -> viewUsers.viewUser();
                    case 3 -> {
                        System.out.println("Выход из меню администратора");
                        startAccount = false;
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

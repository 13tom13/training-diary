package out.menu.authorization.account;

import in.controller.AdminController;
import in.model.User;

import java.util.Scanner;

public class ViewAdminAccount {

    private final AdminController adminController;

    private final Scanner scanner;

    private final User user;
    public ViewAdminAccount(AdminController adminController, User user, Scanner scanner) {
        this.adminController = adminController;
        this.scanner = scanner;
        this.user = user;
    }

    public void adminAccountMenu() {
        boolean startAccount = true;
        while (startAccount) {
            System.out.printf("\nДобро пожаловать %s %s!\n", user.getFirstName(), user.getLastName());
            System.out.println("Выберите действие:");
            System.out.println("1. Просмотр всех тренировок");
            System.out.println("2. Добавление тренировки");
            System.out.println("3. Удаление тренировки");
            System.out.println("4. Внести изменение в тренировку");
            System.out.println("5. Статистика по тренировкам");
            System.out.println("6. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:
                    System.out.println("До свидания!");
                    startAccount = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
                    break;
            }
        }
    }
}

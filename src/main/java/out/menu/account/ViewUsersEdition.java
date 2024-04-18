package out.menu.account;

import in.controller.users.AdminController;
import model.Rights;
import model.User;

import java.util.List;
import java.util.Scanner;

public class ViewUsersEdition {

    private final AdminController adminController;

    private final Scanner scanner;

    public ViewUsersEdition(AdminController adminController, Scanner scanner) {
        this.scanner = scanner;
        this.adminController = adminController;
    }

    public void userEdition(User user) {
        boolean editing = true;
        while (editing) {
            System.out.println();
            System.out.println("Редактирование пользователя: " + user);
            System.out.println("Выберите действие:");
            System.out.println("1. изменить имя");
            System.out.println("2. изменить фамилию");
            System.out.println("3. изменить пароль");
            System.out.println("4. изменить права");
            if (user.isActive()) {
                System.out.println("5. деактивировать пользователя");
            } else {
                System.out.println("5. активировать пользователя");
            }
            System.out.println("6. удалить пользователя");
            System.out.println("7. выход");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        String newName = getNonEmptyInput("Введите новое имя:");
                        adminController.changeUserName(user, newName);
                        break;
                    case 2:
                        String newLastName = getNonEmptyInput("Введите новую фамилию:");
                        adminController.changeUserLastName(user, newLastName);
                        break;
                    case 3:
                        String newPassword = getNonEmptyInput("Введите новый пароль:");
                        adminController.changeUserPassword(user, newPassword);
                        break;
                    case 4:
                        changeUserRights(user);
                        break;
                    case 5:
                        adminController.changeUserActive(user);
                        break;
                    case 6:
                        adminController.deleteUser(user);
                    case 7:
                        System.out.println("До свидания!");
                        editing = false;
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


    private String getNonEmptyInput(String prompt) {
        String input = "";
        while (input.isEmpty()) {
            System.out.println(prompt);
            input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Пожалуйста, введите непустое значение.");
            }
        }
        return input;
    }


    private void changeUserRights(User user) {
        List<Rights> userRights = user.getRights();
        List<Rights> allRights = List.of(Rights.values());

        // Реализуем выбор добавления, удаления или выхода
        boolean editingRights = true;
        while (editingRights) {
            // Выводим текущие права пользователя с нумерацией
            System.out.println("Текущие права пользователя:");
            for (int i = 0; i < userRights.size(); i++) {
                System.out.println((i + 1) + ". " + userRights.get(i));
            }
            System.out.println();

            // Выводим доступные права для изменения с нумерацией
            System.out.println("Доступные права для изменения:");
            for (int i = 0; i < allRights.size(); i++) {
                if (!userRights.contains(allRights.get(i))) {
                    System.out.println((i + 1) + ". " + allRights.get(i));
                }
            }
            System.out.println();

            System.out.println("Выберите действие:");
            System.out.println("1. Добавить право");
            System.out.println("2. Удалить право");
            System.out.println("3. Выход");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Введите номер права для добавления:");
                        int addIndex = scanner.nextInt();
                        scanner.nextLine();
                        if (addIndex >= 1 && addIndex <= allRights.size() &&
                                !userRights.contains(allRights.get(addIndex - 1))) {
                            userRights.add(allRights.get(addIndex - 1));
                        } else {
                            System.out.println("Неверный номер права или право уже добавлено.");
                        }
                        break;
                    case 2:
                        System.out.println("Введите номер права для удаления:");
                        int removeIndex = scanner.nextInt();
                        scanner.nextLine();
                        if (removeIndex >= 1 && removeIndex <= userRights.size()) {
                            userRights.remove(removeIndex - 1);
                        } else {
                            System.out.println("Неверный номер права.");
                        }
                        break;
                    case 3:
                        adminController.changeUserRights(user, userRights);
                        editingRights = false;
                        System.out.println("Список прав пользователя обновлен");
                        break;
                    default:
                        System.out.println("Неверный выбор.");
                        break;
                }
            } else {
                System.out.println("Неверный выбор. Попробуйте еще раз.");
                scanner.nextLine();
            }
        }
    }


}

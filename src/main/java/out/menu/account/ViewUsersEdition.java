package out.menu.account;

import entity.dto.UserDTO;
import exceptions.RepositoryException;
import in.controller.users.AdminController;
import entity.model.Rights;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Класс ViewUsersEdition представляет меню для редактирования пользователей.
 */
@Component
@RequiredArgsConstructor
public class ViewUsersEdition {

    private final AdminController adminController;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Метод для редактирования данных пользователя.
     *
     * @param userDTO Пользователь, данные которого будут редактироваться.
     */
    public void userEdition(UserDTO userDTO) {
        boolean editing = true;
        while (editing) {
            System.out.println();
            System.out.println("Редактирование пользователя: " + userDTO);
            System.out.println("Выберите действие:");
            System.out.println("1. изменить имя");
            System.out.println("2. изменить фамилию");
            System.out.println("3. изменить пароль");
            System.out.println("4. изменить права");
            if (userDTO.isActive()) {
                System.out.println("5. деактивировать пользователя");
            } else {
                System.out.println("5. активировать пользователя");
            }
            System.out.println("6. выход");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
//                    case 1:
//                        String newName = getNonEmptyInput("Введите новое имя:");
//                        try {
//                            userDTO = adminController.changeUserName(userDTO, newName);
//                        } catch (RepositoryException e) {
//                            System.err.println("Ошибка при изменении пользователя: " + e.getMessage());
//                        }
//                        break;
//                    case 2:
//                        String newLastName = getNonEmptyInput("Введите новую фамилию:");
//                        try {
//                            userDTO = adminController.changeUserLastName(userDTO, newLastName);
//                        } catch (RepositoryException e) {
//                            System.err.println("Ошибка при изменении пользователя: " + e.getMessage());
//                        }
//                        break;
//                    case 3:
//                        String newPassword = getNonEmptyInput("Введите новый пароль:");
//                        try {
//                            userDTO = adminController.changeUserPassword(userDTO, newPassword);
//                        } catch (RepositoryException e) {
//                            System.err.println("Ошибка при изменении пользователя: " + e.getMessage());
//                        }
//                        break;
//                    case 4:
//                        try {
//                            changeUserRights(userDTO);
//                        } catch (IOException | RepositoryException e) {
//                            System.err.println("Ошибка при изменении прав пользователя: " + e.getMessage());
//                        }
//                        break;
//                    case 5:
//                        try {
//                            userDTO = adminController.changeUserActive(userDTO);
//                        } catch (RepositoryException e) {
//                            System.err.println("Ошибка при изменении пользователя: " + e.getMessage());
//                        }
//                        break;
                    case 6:
                        System.out.println("Выход из редактирования пользователя " + userDTO.getEmail());
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

    /**
     * Метод для получения непустого ввода от пользователя.
     *
     * @param prompt Сообщение-приглашение для ввода.
     * @return Введенная пользователем строка.
     */
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

    /**
     * Метод для изменения прав пользователя.
     *
     * @param userDTO Пользователь, права которого будут изменены.
     */
    private void changeUserRights(UserDTO userDTO) throws IOException, RepositoryException {
//        List<Rights> userRights = userDTO.getRights();
//        List<Rights> allRights = adminController.getAllRights();
//
//        // Реализуем выбор добавления, удаления или выхода
//        boolean editingRights = true;
//        while (editingRights) {
//            // Выводим текущие права пользователя с нумерацией
//            System.out.println("Текущие права пользователя:");
//            for (int i = 0; i < userRights.size(); i++) {
//                System.out.println((i + 1) + ". " + userRights.get(i));
//            }
//            System.out.println();
//
//            // Выводим доступные права для изменения с нумерацией
//            System.out.println("Доступные права для изменения:");
//            for (int i = 0; i < allRights.size(); i++) {
//                if (!userRights.contains(allRights.get(i))) {
//                    System.out.println((i + 1) + ". " + allRights.get(i));
//                }
//            }
//            System.out.println();
//
//            System.out.println("Выберите действие:");
//            System.out.println("1. Добавить право");
//            System.out.println("2. Удалить право");
//            System.out.println("3. Выход");
//
//            if (scanner.hasNextInt()) {
//                int choice = scanner.nextInt();
//                scanner.nextLine();
//                switch (choice) {
//                    case 1:
//                        System.out.println("Введите номер права для добавления:");
//                        int addIndex = scanner.nextInt();
//                        scanner.nextLine();
//                        if (addIndex >= 1 && addIndex <= allRights.size() &&
//                            !userRights.contains(allRights.get(addIndex - 1))) {
//                            userRights.add(allRights.get(addIndex - 1));
//                        } else {
//                            System.out.println("Неверный номер права или право уже добавлено.");
//                        }
//                        break;
//                    case 2:
//                        System.out.println("Введите номер права для удаления:");
//                        int removeIndex = scanner.nextInt();
//                        scanner.nextLine();
//                        if (removeIndex >= 1 && removeIndex <= userRights.size()) {
//                            userRights.remove(removeIndex - 1);
//                        } else {
//                            System.out.println("Неверный номер права.");
//                        }
//                        break;
//                    case 3:
//                        adminController.changeUserRights(userDTO, userRights);
//                        editingRights = false;
//                        System.out.println("Список прав пользователя обновлен");
//                        break;
//                    default:
//                        System.out.println("Неверный выбор.");
//                        break;
//                }
//            } else {
//                System.out.println("Неверный выбор. Попробуйте еще раз.");
//                scanner.nextLine();
//            }
//        }
    }
}

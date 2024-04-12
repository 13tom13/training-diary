package out.menu;

import in.controller.AuthorizationController;
import in.controller.UserController;
import in.controller.training.TrainingController;
import out.menu.authorization.ViewAuthorization;

import java.util.Scanner;

public class ViewMenu {

    private final ViewAuthorization viewAuthorization;

    public ViewMenu(AuthorizationController authorizationController, 
                    UserController userController, 
                    TrainingController trainingController ) {
        Scanner scanner = new Scanner(System.in);
        this.viewAuthorization = new ViewAuthorization(authorizationController,userController, trainingController, scanner);
    }

    public void displayWelcomeMessage() {
        System.out.println("Добро пожаловать в тренировочный дневник!");
    }

    public void displayMainMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1. Авторизация");
        System.out.println("2. Регистрация");
        System.out.println("3. Выход");
    }

    public void processMainMenuChoice() {
        Scanner scanner = new Scanner(System.in);
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
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте еще раз.");
                break;
        }
    }
}

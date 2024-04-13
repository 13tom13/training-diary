package out.menu.authorization.account;

import in.controller.training.TrainingController;
import in.model.User;
import out.menu.authorization.account.training.ViewTraining;
import out.menu.authorization.account.training.ViewTrainingAdded;
import out.menu.authorization.account.training.ViewTrainingEditing;

import java.util.Scanner;

public class ViewUserAccount {
    private final ViewTraining viewTraining;

    private final ViewTrainingAdded viewTrainingAdded;

    private final ViewTrainingEditing viewTrainingEditing;

    private final Scanner scanner;

    private final User user;


    public ViewUserAccount(TrainingController trainingController, User user, Scanner scanner) {
        this.viewTraining = new ViewTraining(trainingController, user);
        this.viewTrainingAdded = new ViewTrainingAdded(trainingController, user, scanner);
        this.viewTrainingEditing = new ViewTrainingEditing(trainingController, viewTrainingAdded, user, scanner);
        this.scanner = scanner;
        this.user = user;
    }

    public void userAccountMenu() {
        boolean startAccount = true;
        while (startAccount) {
            System.out.printf("\nДобро пожаловать %s %s!\n", user.getFirstName(), user.getLastName());
            System.out.println("Выберите действие:");
            System.out.println("1. Просмотр всех тренировок");
            System.out.println("2. Добавление тренировки");
            System.out.println("3. Внести изменение в тренировку");
            System.out.println("4. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    viewTraining.viewAllTraining();
                    //TODO: сообщение о том что список тренировок пуст
                    break;
                case 2:
                    viewTrainingAdded.addTraining();
                    break;
                case 3:
                    viewTrainingEditing.editingTraining();
                    break;
                case 4:
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


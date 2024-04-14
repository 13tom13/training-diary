package out.menu.account;

import in.controller.TrainingController;
import in.controller.TrainingStatisticsController;
import in.model.User;
import out.menu.training.ViewTrainingAdded;
import out.menu.training.ViewTrainingEditing;
import out.menu.statistic.ViewTrainingStatistcs;
import out.menu.training.ViewTraining;

import java.util.Scanner;

public class ViewUserAccount {
    private final ViewTraining viewTraining;

    private final ViewTrainingAdded viewTrainingAdded;

    private final ViewTrainingEditing viewTrainingEditing;

    private final ViewTrainingStatistcs viewTrainingStatistcs;

    private final Scanner scanner;

    private final User user;


    public ViewUserAccount(TrainingController trainingController, TrainingStatisticsController trainingStatisticsController, User user, Scanner scanner) {
        this.viewTrainingStatistcs = new ViewTrainingStatistcs(trainingStatisticsController, user, scanner);
        this.viewTraining = new ViewTraining(trainingController);
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
            System.out.println("3. Удаление тренировки");
            System.out.println("4. Внести изменение в тренировку");
            System.out.println("5. Статистика по тренировкам");
            System.out.println("6. Выход");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        viewTraining.viewAllTraining(user);
                        break;
                    case 2:
                        viewTrainingAdded.addTraining();
                        break;
                    case 3:
                        viewTrainingAdded.deleteTraining();
                        break;
                    case 4:
                        viewTrainingEditing.editingTraining();
                        break;
                    case 5:
                        viewTrainingStatistcs.statisticMenu();
                        break;
                    case 6:
                        System.out.println("До свидания!");
                        startAccount = false;
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


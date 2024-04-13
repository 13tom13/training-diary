package out.menu.authorization.account.statistic;

import in.controller.training.TrainingStatisticsController;
import in.controller.training.implementation.TrainingStatisticsControllerImpl;
import in.model.User;

import java.util.Scanner;

public class ViewTrainingStatistcs {

    private final TrainingStatisticsController trainingStatisticsController;

    private final User user;

    private final Scanner scanner;

    public ViewTrainingStatistcs(TrainingStatisticsController trainingStatisticsController, User user, Scanner scanner) {
        this.trainingStatisticsController = trainingStatisticsController;
        this.user = user;
        this.scanner = scanner;
    }

    public void statisticMenu() {
        boolean start = true;
        while (start) {
            System.out.printf("\nСтатистика пользователя:%s %s!\n", user.getFirstName(), user.getLastName());
            System.out.println("Выберите действие:");
            System.out.println("1. Всего тренировок");
            System.out.println("2. Всего тренировок за период");
            System.out.println("3. Общая длительность тренировок за период");
            System.out.println("4. Калорий сожжено за период");
            System.out.println("5. Выход");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        printTotalTrainingStatistics();
                        break;
                    case 2:
                        printPeriodTrainingStatistics(trainingStatisticsController::getAllTrainingStatisticsPerPeriod);
                        break;
                    case 3:
                        printPeriodTrainingStatistics(trainingStatisticsController::getDurationStatisticsPerPeriod);
                        break;
                    case 4:
                        printPeriodTrainingStatistics(trainingStatisticsController::getCaloriesBurnedPerPeriod);
                        break;
                    case 5:
                        System.out.println("До свидания!");
                        start = false;
                        break;
                    default:
                        System.out.println("Неверный выбор. Попробуйте еще раз.");
                        break;
                }
            } else {
                System.out.println("Неверный выбор. Попробуйте еще раз.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }


    private void printTotalTrainingStatistics() {
        int result = trainingStatisticsController.getAllTrainingStatistics(user);
        if (result != 0) {
            System.out.println("Всего тренировок пользователя: " + result);
        } else {
            System.out.println("Тренировок не обнаружено");
        }
    }

    private void printPeriodTrainingStatistics(TriFunction<User, String, String, Integer> statisticsFunction) {
        System.out.println("Введите начало периода:");
        String startDate = scanner.nextLine();
        System.out.println("Введите конец периода:");
        String endDate = scanner.nextLine();
        int result = statisticsFunction.apply(user, startDate, endDate);
        if (result != 0) {
            System.out.println("Результат: " + result);
        } else {
            System.out.println("Нет данных за указанный период");
        }
    }


}

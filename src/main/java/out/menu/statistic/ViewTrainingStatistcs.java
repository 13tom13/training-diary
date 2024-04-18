package out.menu.statistic;

import in.controller.training.TrainingStatisticsController;
import model.User;

import java.util.Scanner;

/**
 * Класс ViewTrainingStatistcs представляет меню для просмотра статистики тренировок пользователя.
 */
public class ViewTrainingStatistcs {

    private final TrainingStatisticsController trainingStatisticsController;
    private final User user;
    private final Scanner scanner;

    /**
     * Конструктор класса ViewTrainingStatistcs.
     *
     * @param trainingStatisticsController Контроллер статистики тренировок.
     * @param user                          Пользователь, для которого отображается статистика.
     * @param scanner                       Сканер для ввода данных.
     */
    public ViewTrainingStatistcs(TrainingStatisticsController trainingStatisticsController, User user, Scanner scanner) {
        this.trainingStatisticsController = trainingStatisticsController;
        this.user = user;
        this.scanner = scanner;
    }

    /**
     * Метод для отображения главного меню статистики тренировок пользователя.
     */
    public void statisticMenu() {
        boolean start = true;
        while (start) {
            System.out.printf("\nСтатистика пользователя: %s %s!\n", user.getFirstName(), user.getLastName());
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
                    case 1 -> printTotalTrainingStatistics();
                    case 2 -> printPeriodTrainingStatistics(trainingStatisticsController::getAllTrainingStatisticsPerPeriod);
                    case 3 -> printPeriodTrainingStatistics(trainingStatisticsController::getDurationStatisticsPerPeriod);
                    case 4 -> printPeriodTrainingStatistics(trainingStatisticsController::getCaloriesBurnedPerPeriod);
                    case 5 -> {
                        System.out.println("Выход из меню статистики пользователя " + user.getEmail());
                        start = false;
                    }
                    default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
                }
            } else {
                System.out.println("Неверный выбор. Попробуйте еще раз.");
                scanner.nextLine(); // Считываем неверный ввод
            }
        }
    }

    /**
     * Метод для вывода общей статистики всех тренировок пользователя.
     */
    private void printTotalTrainingStatistics() {
        int result = trainingStatisticsController.getAllTrainingStatistics(user);
        if (result != 0) {
            System.out.println("Всего тренировок пользователя: " + result);
        } else {
            System.out.println("Тренировок не обнаружено");
        }
    }

    /**
     * Метод для вывода статистики тренировок пользователя за определенный период.
     *
     * @param statisticsFunction Функция для получения статистики за период.
     */
    private void printPeriodTrainingStatistics(TriFunction<User, String, String, Integer> statisticsFunction) {
        System.out.println("Введите начало периода:");
        String startDate = scanner.nextLine();
        System.out.println("Введите конец периода:");
        String endDate = scanner.nextLine();
        int result = statisticsFunction.apply(user, startDate, endDate);
        if (result == -1) {
            System.err.println("Ошибка");
            return;
        }
        if (result != 0) {
            System.out.println("Результат: " + result);
        } else {
            System.out.println("Нет данных за указанный период");
        }
    }
}
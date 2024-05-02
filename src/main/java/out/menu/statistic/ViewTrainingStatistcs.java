package out.menu.statistic;


import config.initializer.in.ControllerFactory;
import entities.dto.UserDTO;
import in.controller.training.TrainingStatisticsController;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import static utils.Utils.getDateFromString;

/**
 * Класс ViewTrainingStatistcs представляет меню для просмотра статистики тренировок пользователя.
 */
public class ViewTrainingStatistcs {

    private final TrainingStatisticsController trainingStatisticsController;
    private final UserDTO userDTO;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Конструктор класса ViewTrainingStatistcs.
     *
     * @param userDTO                          Пользователь, для которого отображается статистика.
     */
    public ViewTrainingStatistcs(UserDTO userDTO) {
        this.trainingStatisticsController = ControllerFactory.getInstance().getTrainingStatisticsController();
        this.userDTO = userDTO;
    }

    /**
     * Метод для отображения главного меню статистики тренировок пользователя.
     */
    public void statisticMenu() {
        boolean start = true;
        while (start) {
            System.out.printf("\nСтатистика пользователя: %s %s!\n", userDTO.getFirstName(), userDTO.getLastName());
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
                        System.out.println("Выход из меню статистики пользователя " + userDTO.getEmail());
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
        int result = trainingStatisticsController.getAllTrainingStatistics(userDTO);
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
    private void printPeriodTrainingStatistics(TriFunction<UserDTO, Date, Date, Integer> statisticsFunction) {
        System.out.println("Введите начало периода (дд.мм.гг):");
        String stringStartDate = scanner.nextLine();
        Date startDate;
        try {
            startDate = getDateFromString(stringStartDate);
        } catch (ParseException e) {
            System.err.println("Неверный формат даты. Пожалуйста, введите дату в формате дд.мм.гг.");
            return; // Выйти из метода, если дата некорректна
        }

        System.out.println("Введите конец периода (дд.мм.гг):");
        String stringEndDate = scanner.nextLine();
        Date endDate;
        try {
            endDate = getDateFromString(stringEndDate);
        } catch (ParseException e) {
            System.err.println("Неверный формат даты. Пожалуйста, введите дату в формате дд.мм.гг.");
            return; // Выйти из метода, если дата некорректна
        }

        int result = statisticsFunction.apply(userDTO, startDate, endDate);
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
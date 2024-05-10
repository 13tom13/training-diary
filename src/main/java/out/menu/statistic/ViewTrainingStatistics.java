package out.menu.statistic;


import entity.dto.UserDTO;
import in.controller.training.statistics.TrainingStatisticsController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Scanner;

import static utils.Utils.enterStringDate;
import static utils.Utils.getDateFromString;

/**
 * Класс ViewTrainingStatistics представляет меню для просмотра статистики тренировок пользователя.
 */
@Component
@RequiredArgsConstructor
public class ViewTrainingStatistics {

    private final TrainingStatisticsController trainingStatisticsController;
    private UserDTO userDTO;
    private final Scanner scanner = new Scanner(System.in);

    public void setUserDTO(UserDTO userDTO) {
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
//                    case 2 -> printPeriodTrainingStatistics(trainingStatisticsController::getAllTrainingStatisticsPerPeriod);
//                    case 3 -> printPeriodTrainingStatistics(trainingStatisticsController::getDurationStatisticsPerPeriod);
//                    case 4 -> printPeriodTrainingStatistics(trainingStatisticsController::getCaloriesBurnedPerPeriod);
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
//        int result = trainingStatisticsController.getAllTrainingStatistics(userDTO);
//        if (result > 0) {
//            System.out.println("Всего тренировок пользователя: " + result);
//        } else {
//            System.out.println("Тренировок не обнаружено");
//        }
    }

    /**
     * Метод для вывода статистики тренировок пользователя за определенный период.
     *
     * @param statisticsFunction Функция для получения статистики за период.
     */
    private void printPeriodTrainingStatistics(TriFunction<UserDTO, LocalDate, LocalDate, Integer> statisticsFunction) {
        System.out.println("Введите начало периода (дд.мм.гг):");
        String stringStartDate = enterStringDate(scanner);
        LocalDate startDate = getDateFromString(stringStartDate);

        System.out.println("Введите конец периода (дд.мм.гг):");
        String stringEndDate = enterStringDate(scanner);
        LocalDate endDate = getDateFromString(stringEndDate);

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
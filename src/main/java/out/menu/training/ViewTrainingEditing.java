package out.menu.training;

import in.controller.training.TrainingController;
import entities.model.Training;
import entities.model.User;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.TreeSet;

import static utils.Utils.*;

/**
 * Представляет класс для редактирования тренировок пользователя.
 */
public class ViewTrainingEditing {

    private final TrainingController trainingController;
    private final ViewTrainingAdded viewTrainingAdded;
    private final User user;
    private final Scanner scanner;

    private Training training;

    /**
     * Создает экземпляр ViewTrainingEditing с заданным контроллером тренировок, представлением добавления тренировок, пользователем и сканером.
     *
     * @param trainingController Контроллер тренировок.
     * @param viewTrainingAdded Представление добавления тренировок.
     * @param user              Пользователь.
     * @param scanner           Сканер для ввода данных.
     */
    public ViewTrainingEditing(TrainingController trainingController,
                               ViewTrainingAdded viewTrainingAdded,
                               User user, Scanner scanner) {
        this.trainingController = trainingController;
        this.viewTrainingAdded = viewTrainingAdded;
        this.user = user;
        this.scanner = scanner;
    }

    /**
     * Получает тренировку для редактирования.
     */
    private void getTrainingForEditing() {
        TreeSet<Training> trainingsFromDay;
        System.out.println("Введите дату тренировки: ");
        String stringDate = scanner.nextLine();
        Date trainingDate;
        try {
            trainingDate = getDateFromString(stringDate);
        } catch (ParseException e) {
            System.err.println("Неверный формат даты. Пожалуйста, введите дату в формате " + DATE_FORMAT);
            return; // Выйти из метода, если дата некорректна
        }
        trainingsFromDay = trainingController.getTrainingsByUserEmailAndData(user, trainingDate);
        if (!trainingsFromDay.isEmpty()) {
            System.out.println("Тренировки на " + getFormattedDate(trainingDate) + ":");
            for (Training training : trainingsFromDay) {
                System.out.println(training);
            }
            System.out.println("Введите название тренировки: ");
            String trainingName = scanner.nextLine();
            training = trainingController.getTrainingByUserEmailAndDateAndName(user, trainingDate, trainingName);
        }
    }


    /**
     * Редактирует тренировку.
     */
    public void editingTraining() {
        getTrainingForEditing();
        boolean startView = false;
        if (training != null && training.getName() != null) {
            startView = true;
        } else {
            System.out.println("Тренировка не найдена.");
        }
        while (startView) {
            System.out.println();
            System.out.printf("Редактирование тренировки:\n%s", training);
            System.out.println();
            System.out.println("Выберите действие:");
            System.out.println("1. изменить название");
            System.out.println("2. изменить дату тренировки");
            System.out.println("3. изменить продолжительность");
            System.out.println("4. изменить сожженные калории");
            System.out.println("5. изменить дополнительную информацию");
            System.out.println("6. Выход");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> {
                        System.out.println();
                        System.out.println("Введите новое название:");
                        String newName = scanner.nextLine();
                        training  = trainingController.changeNameTraining(user, training, newName);
                    }
                    case 2 -> {
                        System.out.println();
                        System.out.println("Введите новую дату:");
                        String stringDate = scanner.nextLine();

                        try {
                            Date newDate = getDateFromString(stringDate);
                            training = trainingController.changeDateTraining(user, training, newDate);
                        } catch (ParseException e) {
                            System.err.println("Неверный формат даты. Пожалуйста, введите дату в формате " + DATE_FORMAT);
                        }
                    }
                    case 3 -> {
                        System.out.println();
                        System.out.println("Введите новую продолжительность:");
                        String newDuration = scanner.nextLine();
                        training = trainingController.changeDurationTraining(user, training, newDuration);
                    }
                    case 4 -> {
                        System.out.println();
                        System.out.println("Введите новое количество сожженных калорий:");
                        String newCalories = scanner.nextLine();
                        training = trainingController.changeCaloriesTraining(user, training, newCalories);
                    }
                    case 5 -> viewTrainingAdded.addTrainingAdditional(training);
                    case 6 -> {
                        System.out.println("Выход из меню изменения тренировки.");
                        startView = false;
                    }
                    default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
                }
            } else {
                System.out.println("Неверный ввод. Пожалуйста, введите число.");
                scanner.nextLine();
            }
        }
    }


}

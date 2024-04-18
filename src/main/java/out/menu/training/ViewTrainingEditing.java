package out.menu.training;

import in.controller.training.TrainingController;
import model.Training;
import model.User;

import java.util.Scanner;
import java.util.TreeSet;

import static utils.DateValidationService.isValidDateFormat;

public class ViewTrainingEditing {

    private final TrainingController trainingController;

    private final ViewTrainingAdded viewTrainingAdded;
    private final User user;
    private final Scanner scanner;

    private Training training;

    public ViewTrainingEditing(TrainingController trainingController,
                               ViewTrainingAdded viewTrainingAdded,
                               User user, Scanner scanner) {
        this.trainingController = trainingController;
        this.viewTrainingAdded = viewTrainingAdded;
        this.user = user;
        this.scanner = scanner;
    }

    private void getTrainingForEditing() {
        TreeSet<Training> trainingsFromDay;
        System.out.println("Введите дату тренировки: ");
        String trainingDate = scanner.nextLine();
        trainingsFromDay = trainingController.getTrainingsByUserEmailAndData(user, trainingDate);
        if (!trainingsFromDay.isEmpty()) {
            System.out.println("Тренировки на " + trainingDate + ":");
            for (Training training : trainingsFromDay) {
                System.out.println(training);
            }
            System.out.println("Введите название тренировки: ");
            String trainingName = scanner.nextLine();
            training = trainingController.getTrainingByUserEmailAndDataAndName(user, trainingDate, trainingName);
        }
    }


    public void editingTraining() {
        getTrainingForEditing();
        boolean startView = false;
        if (training != null && training.getName()!= null ) {
            startView = true;
        } else {
            System.out.println("Тренировка не найдена.");
        }
        while (startView) {
            System.out.println();
            System.out.printf("Редактирование тренировки:\n%s", training);
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
                    case 1:
                        System.out.println();
                        System.out.println("Введите новое название:");
                        String newName = scanner.nextLine();
                        trainingController.changeNameTraining(user, training, newName);
                        break;
                    case 2:
                        System.out.println();
                        System.out.println("Введите новую дату:");
                        String newDate = scanner.nextLine();
                        if (isValidDateFormat(newDate)) {
                            trainingController.changeDateTraining(user, training, newDate);
                        } else {
                            System.err.println("Неверный формат даты. Пожалуйста, введите дату в формате дд.мм.гг.");
                        }
                        break;
                    case 3:
                        System.out.println();
                        System.out.println("Введите новую продолжительность:");
                        String newDuration = scanner.nextLine();
                        trainingController.changeDurationTraining(user, training, newDuration);
                        break;
                    case 4:
                        System.out.println();
                        System.out.println("Введите новое количество сожженных калорий:");
                        String newCalories = scanner.nextLine();
                        trainingController.changeCaloriesTraining(user, training, newCalories);
                        break;
                    case 5:
                        viewTrainingAdded.addTrainingAdditional(user, training);
                        break;
                    case 6:
                        System.out.println("Выход из меню изменения тренировки");
                        startView = false;
                        break;
                    default:
                        System.out.println("Неверный выбор. Попробуйте еще раз.");
                        break;
                }
            } else {
                System.out.println("Неверный ввод. Пожалуйста, введите число.");
                scanner.nextLine();
            }
        }
    }
}

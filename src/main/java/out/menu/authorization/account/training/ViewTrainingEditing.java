package out.menu.authorization.account.training;

import in.controller.training.TrainingController;
import in.model.Training;
import in.model.User;

import java.util.List;
import java.util.Scanner;

public class ViewTrainingEditing {

    private final TrainingController trainingController;

    private final ViewTrainingAdded viewTrainingAdded;
    private final User user;
    private final Scanner scanner;

    public ViewTrainingEditing(TrainingController trainingController,
                               ViewTrainingAdded viewTrainingAdded,
                               User user, Scanner scanner) {
        this.trainingController = trainingController;
        this.viewTrainingAdded = viewTrainingAdded;
        this.user = user;
        this.scanner = scanner;
    }

    private Training getTrainingForEditing() {
        List<Training> trainingsFromDay;
        System.out.println("Введите дату тренировки: ");
        String trainingDate = scanner.nextLine();
        trainingsFromDay = trainingController.getTrainingsByUserAndDay(user, trainingDate);

        System.out.println("Тренировки на " + trainingDate + ":");
        for (Training training : trainingsFromDay) {
            System.out.println(training);
        }

        System.out.println("Введите название тренировки: ");
        String trainingName = scanner.nextLine();
        return trainingController.getTrainingByNameAndDate(trainingName, trainingDate);
        //TODO реализовать метод getTrainingByNameAndDate
    }


    public void editingTraining() {
        boolean startView = true;
        while (startView) {
            System.out.println();
            Training training = getTrainingForEditing();

            System.out.printf("Редактирование тренировки %s дата: %s:\n", training.getName(), training.getDate());
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
                        System.out.println("Введите новое название:");
                        String newName = scanner.nextLine();
                        trainingController.changeNameTraining(training, newName);
                        break;
                    case 2:
                        System.out.println("Введите новую дату:");
                        String newDate = scanner.nextLine();
                        trainingController.changeDateTraining(training, newDate);
                        break;
                    case 3:
                        System.out.println("Введите новую продолжительность:");
                        String newDuration = scanner.nextLine();
                        trainingController.changeDurationTraining(training, newDuration);
                        break;
                    case 4:
                        System.out.println("Введите новое количество сожженных калорий:");
                        String newCalories = scanner.nextLine();
                        trainingController.changeCaloriesTraining(training, newCalories);
                        break;
                    case 5:
                        viewTrainingAdded.addTrainingAdditional(training);
                        break;
                    case 6:
                        System.out.println("До свидания!");
                        startView = false;
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

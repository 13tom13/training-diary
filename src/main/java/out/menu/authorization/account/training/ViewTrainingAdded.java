package out.menu.authorization.account.training;

import in.controller.training.TrainingController;
import in.model.Training;
import in.model.User;

import java.util.Scanner;

import static in.service.implementation.DateValidationService.isValidDateFormat;

public class ViewTrainingAdded {

    private final TrainingController trainingController;

    private final User user;

    private final Scanner scanner;

    public ViewTrainingAdded(TrainingController trainingController, User user, Scanner scanner) {
        this.trainingController = trainingController;
        this.user = user;
        this.scanner = scanner;
    }

    public void addTraining() {
        System.out.println("Введите данные тренировки:");
        System.out.print("Название: ");
        String name = scanner.nextLine();
        System.out.print("Дата (дд.мм.гг): ");
        String date = scanner.nextLine();
        if (isValidDateFormat(date)) {
            System.out.print("Продолжительность (минуты): ");
            int duration = Integer.parseInt(scanner.nextLine());
            System.out.print("Сожженные калории: ");
            int caloriesBurned = Integer.parseInt(scanner.nextLine());
            Training training = new Training(name, date, duration, caloriesBurned);
            trainingController.saveTraining(user, training);
            addTrainingAdditional(user, training);
        } else {
            System.err.println("Неверный формат даты. Пожалуйста, введите дату в формате дд.мм.гг.");
        }
    }

    public void deleteTraining() {
        System.out.print("Введите дату тренировки (дд.мм.гг): ");
        String date = scanner.nextLine();
        System.out.print("Название: ");
        String name = scanner.nextLine();
        trainingController.deleteTraining(user, date, name);
    }

    public void addTrainingAdditional(User user, Training training) {
        boolean startAdd = true;
        while (startAdd) {
            System.out.println("1. Добавить дополнительную информацию?");
            if (!training.getAdditions().isEmpty()){
                System.out.println("2. удалить дополнительную информацию?");
            }
            System.out.println("3. выход");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Введите дополнительную информацию:");
                        System.out.println("Название:");
                        String additionalName = scanner.nextLine();
                        System.out.println("Значение:");
                        String additionalValue = scanner.nextLine();
                        trainingController.addTrainingAdditional(user, training, additionalName, additionalValue);
                        break;
                    case 2:
                        if (!training.getAdditions().isEmpty()){
                            System.out.println("Введите название дополнительной информации для удаления:");
                            String additionalNameForRemove = scanner.nextLine();
                            trainingController.removeTrainingAdditional(user, training, additionalNameForRemove);
                            break;
                        }
                    case 3:
                        System.out.println("Выход.");
                        startAdd = false;
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

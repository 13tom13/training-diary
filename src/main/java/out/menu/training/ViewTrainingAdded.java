package out.menu.training;

import in.controller.training.TrainingController;
import model.Training;
import model.User;

import java.util.List;
import java.util.Scanner;

import static utils.DateValidationService.isValidDateFormat;

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
        String name = choseTrainingType();
        System.out.print("Дата (дд.мм.гг): ");
        String date = scanner.nextLine();
        if (isValidDateFormat(date)) {
            System.out.print("Продолжительность (минуты): ");
            int duration = Integer.parseInt(scanner.nextLine());
            System.out.print("Сожженные калории: ");
            int caloriesBurned = Integer.parseInt(scanner.nextLine());
            Training training = new Training(name, date, duration, caloriesBurned);
            if (trainingController.saveTraining(user, training)){
                addTrainingAdditional(user, training);
            }
        } else {
            System.err.println("Неверный формат даты. Пожалуйста, введите дату в формате дд.мм.гг.");
        }
    }

    public String choseTrainingType() {
        System.out.println("Выберите тип тренировки:");

        List<String> trainingTypes = trainingController.getTrainingTypes(user);

        for (int i = 0; i < trainingTypes.size(); i++) {
            System.out.println((i + 1) + ". " + trainingTypes.get(i));
        }

        System.out.println((trainingTypes.size() + 1) + ". Ввести свой тип тренировки");

        System.out.print("Ваш выбор: ");
        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= trainingTypes.size()) {
            String selectedTrainingType = trainingTypes.get(choice - 1);
            System.out.println("Выбран тип тренировки: " + selectedTrainingType);
            scanner.nextLine();
            return selectedTrainingType;
        } else if (choice == trainingTypes.size() + 1) {
            scanner.nextLine();
            System.out.print("Введите свой тип тренировки: ");
            String customTrainingType = scanner.nextLine();
            trainingController.saveTrainingType(user, customTrainingType);
            System.out.println("Выбран пользовательский тип тренировки: " + customTrainingType);
            return customTrainingType;
        } else {
            System.out.println("Некорректный выбор.");
            return null; // или можно выбрасывать исключение или делать что-то ещё, в зависимости от требований системы
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

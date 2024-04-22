package out.menu.training;

import in.controller.training.TrainingController;
import model.Training;
import model.User;

import java.text.ParseException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static utils.Utils.getDateFromString;

/**
 * Представляет класс для добавления и удаления тренировок пользователя.
 */
public class ViewTrainingAdded {

    private final TrainingController trainingController;
    private final User user;
    private final Scanner scanner;

    /**
     * Создает экземпляр ViewTrainingAdded с заданным контроллером тренировок, пользователем и сканером.
     *
     * @param trainingController Контроллер тренировок.
     * @param user               Пользователь.
     * @param scanner            Сканер для ввода данных.
     */
    public ViewTrainingAdded(TrainingController trainingController, User user, Scanner scanner) {
        this.trainingController = trainingController;
        this.user = user;
        this.scanner = scanner;
    }

    /**
     * Метод для добавления тренировки.
     */
    public void addTraining() {
        System.out.println("Введите данные тренировки:");
        System.out.print("Название: ");
        String name = chooseTrainingType();

        // Просим пользователя ввести дату до тех пор, пока он не введет корректную дату
        Date date = null;
        while (date == null) {
            System.out.print("Дата (дд.мм.гг): ");
            String stringDate = scanner.nextLine();
            try {
                date = getDateFromString(stringDate);
            } catch (ParseException e) {
                System.err.println("Неверный формат даты. Пожалуйста, введите дату в формате дд.мм.гг.");
            }
        }

        int duration = 0;
        while (duration <= 0) {
            try {
                System.out.print("Продолжительность (минуты): ");
                duration = Integer.parseInt(scanner.nextLine());
                if (duration <= 0) {
                    System.err.println("Продолжительность должна быть положительным числом.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Неверный формат продолжительности. Пожалуйста, введите целое число.");
            }
        }

        int caloriesBurned = 0;
        while (caloriesBurned <= 0) {
            try {
                System.out.print("Сожженные калории: ");
                caloriesBurned = Integer.parseInt(scanner.nextLine());
                if (caloriesBurned <= 0) {
                    System.err.println("Сожженные калории должны быть положительным числом.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Неверный формат сожженных калорий. Пожалуйста, введите целое число.");
            }
        }

        Training training = new Training(name, date, duration, caloriesBurned);
        if (trainingController.saveTraining(user, training)) {
            addTrainingAdditional(user, training);
            System.out.println("Тренировка успешно сохранена.");
        }
    }


    /**
     * Метод для выбора типа тренировки.
     *
     * @return Выбранный тип тренировки.
     */
    public String chooseTrainingType() {
        System.out.println("Выберите тип тренировки:");

        List<String> trainingTypes = trainingController.getTrainingTypes(user);

        for (int i = 0; i < trainingTypes.size(); i++) {
            System.out.println((i + 1) + ". " + trainingTypes.get(i));
        }

        System.out.println((trainingTypes.size() + 1) + ". Ввести свой тип тренировки");

        while (true) {
            try {
                System.out.print("Ваш выбор: ");
                int choice = scanner.nextInt();

                if (choice >= 1 && choice <= trainingTypes.size()) {
                    String selectedTrainingType = trainingTypes.get(choice - 1);
                    System.out.println("Выбран тип тренировки: " + selectedTrainingType);
                    scanner.nextLine(); // Очистка буфера
                    return selectedTrainingType;
                } else if (choice == trainingTypes.size() + 1) {
                    scanner.nextLine(); // Очистка буфера
                    System.out.print("Введите свой тип тренировки: ");
                    String customTrainingType = scanner.nextLine();
                    trainingController.saveTrainingType(user, customTrainingType);
                    System.out.println("Выбран пользовательский тип тренировки: " + customTrainingType);
                    return customTrainingType;
                } else {
                    System.out.println("Некорректный выбор.");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Очистка буфера
                System.out.println("Некорректный ввод. Пожалуйста, введите число.");
            }
        }

    }


    /**
     * Метод для удаления тренировки.
     */
    public void deleteTraining() {
        System.out.print("Введите дату тренировки (дд.мм.гг): ");
        String stringDate = scanner.nextLine();
        Date date;
        try {
            date = getDateFromString(stringDate);
        } catch (ParseException e) {
            System.err.println("Неверный формат даты. Пожалуйста, введите дату в формате дд.мм.гг.");
            return; // Выйти из метода, если дата некорректна
        }
        System.out.print("Название: ");
        String name = scanner.nextLine();
        if (trainingController.deleteTraining(user, date, name)) {
            System.out.println("Тренировка успешно удалена.");
        }
    }


    /**
     * Метод для добавления дополнительной информации о тренировке.
     *
     * @param user     Пользователь.
     * @param training Тренировка, для которой добавляется информация.
     */
    public void addTrainingAdditional(User user, Training training) {
        boolean startAdd = true;
        while (startAdd) {
            System.out.println("1. Добавить дополнительную информацию?");
            if (!training.getAdditions().isEmpty()) {
                System.out.println("2. Удалить дополнительную информацию?");
            }
            System.out.println("3. Выход");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> {
                        System.out.println("Введите дополнительную информацию:");
                        System.out.println("Название:");
                        String additionalName = scanner.nextLine();
                        System.out.println("Значение:");
                        String additionalValue = scanner.nextLine();
                        trainingController.addTrainingAdditional(user, training, additionalName, additionalValue);
                    }
                    case 2 -> {
                        if (!training.getAdditions().isEmpty()) {
                            System.out.println("Введите название дополнительной информации для удаления:");
                            String additionalNameForRemove = scanner.nextLine();
                            trainingController.removeTrainingAdditional(user, training, additionalNameForRemove);
                        }
                    }
                    case 3 -> {
                        System.out.println("Создание тренировки завершено.");
                        startAdd = false;
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

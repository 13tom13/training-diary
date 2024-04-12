package out;

import controller.AuthorizationController;
import controller.training.implementation.TrainingControllerImpl;
import controller.UserController;
import exception.ServiceException;
import exception.AuthorizationException;
import model.Training;
import model.User;

import java.util.*;

public class TrainingDiary {

    private final AuthorizationController authorizationController;
    private final UserController userController;
    private final TrainingControllerImpl trainingController;

    public TrainingDiary(AuthorizationController authorizationController,
                         UserController userController, TrainingControllerImpl trainingController) {
        this.authorizationController = authorizationController;
        this.userController = userController;
        this.trainingController = trainingController;
    }

    public void start() {
        menu();
    }

    private void menu() {
        Scanner scanner = new Scanner(System.in);
        boolean startMenu = true;
        while (startMenu) {
            System.out.println();
            System.out.println("Добро пожаловать в тренировочный дневник!");
            System.out.println("Выберите действие:");
            System.out.println("1. Авторизация");
            System.out.println("2. Регистрация");
            System.out.println("3. Выход");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        authorization(scanner);
                        break;
                    case 2:
                        registration(scanner);
                        break;
                    case 3:
                        System.out.println("До свидания!");
                        startMenu = false;
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
        scanner.close();
    }


    private void authorization(Scanner scanner) {
        // Выполняем авторизацию
        System.out.println("Вы выбрали авторизацию");
        System.out.println("Введите email:");
        String authEmail = scanner.nextLine();
        System.out.println("Введите пароль:");
        String authPassword = scanner.nextLine();
        try {
            User user = authorizationController.login(authEmail, authPassword);
            account(user, scanner);
        } catch (AuthorizationException | ServiceException e) {
            System.err.println(e.getMessage());
        }
    }

    private void account(User user, Scanner scanner) {
        boolean startAccount = true;
        while (startAccount) {
            System.out.println();
            System.out.printf("Добро пожаловать %s %s!\n", user.getFirstName(), user.getLastName());
            System.out.println("Выберите действие:");
            System.out.println("1. Добавление тренировки");
            System.out.println("2. Просмотр тренировок");
            System.out.println("3. Выход");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        addTraining(user, scanner);
                        break;
                    case 2:
                        viewTraining(user, scanner);
                        break;
                    case 3:
                        System.out.println("До свидания!");
                        startAccount = false;
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

    private void viewTraining(User user, Scanner scanner) {
        boolean startView = true;
        while (startView) {
            System.out.println();
            System.out.printf("Тренировки пользователя %s %s:\n", user.getFirstName(), user.getLastName());
            System.out.println("Выберите действие:");
            System.out.println("1. Просмотр тренировок");
            System.out.println("2. Внести изменение в тренировку");
            System.out.println("3. Выход");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        viewAllTraining(user.getEmail());
                        break;
                    case 2:
                        System.out.println("Введите номер тренировки:");
                        int trainingNumber = scanner.nextInt();
                        Training training = trainingController.getTrainingByNumber(user.getEmail(),
                                trainingNumber-1);
                        editingTraining(training, scanner);
                        break;
                    case 3:
                        System.out.println("До свидания!");
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

    private void editingTraining(Training training, Scanner scanner) {
        boolean startView = true;
        while (startView) {
            System.out.println();
            System.out.printf("Редактирование тренировки %s дата: %s:\n", training.getName(), training.getDate());
            System.out.println("Выберите действие:");
            System.out.println("1. изменить название");
            System.out.println("2. изменить дату тренировки");
            System.out.println("3. изменить продолжительность");
            System.out.println("4. изменить сожженные калории");
            System.out.println("5. Выход");
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

    private void viewAllTraining(String userEmail) {
        TreeSet<Training> allTraining = trainingController.getAllTrainings(userEmail);
        String previousDate = "";
        for (Training training : allTraining) {
            String currentDate = String.valueOf(training.getDate());
            if (!currentDate.equals(previousDate)) {
                System.out.println("\n" + currentDate + ":");
                previousDate = currentDate;
            }
            System.out.println(training);
        }




    }


    private void addTraining(User user, Scanner scanner) {
        System.out.println("Введите данные тренировки:");
        System.out.print("Название: ");
        String name = scanner.nextLine();
        System.out.print("Дата (дд.мм.гг): ");
        String date = scanner.nextLine();
        System.out.print("Продолжительность (минуты): ");
        int duration = Integer.parseInt(scanner.nextLine());
        System.out.print("Сожженные калории: ");
        int caloriesBurned = Integer.parseInt(scanner.nextLine());
        Training training = trainingController.createTraining(user.getEmail(), name, date, duration, caloriesBurned);
        addTrainingAdditional(scanner, training);
    }

    private void addTrainingAdditional(Scanner scanner, Training training) {
        boolean startAdd = true;
        while (startAdd) {
            System.out.println("Добавить дополнительную информацию?");
            System.out.println("1. да");
            System.out.println("2. нет");
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
                        trainingController.addTrainingAdditional(training, additionalName, additionalValue);
                        break;
                    case 2:
                        System.out.println("Тренировка успешно добавлена.");
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

    private void registration(Scanner scanner) {
        // Выполняем регистрацию
        System.out.println("Вы выбрали регистрацию");
        System.out.println("Введите email:");
        String regEmail = scanner.nextLine();
        System.out.println("Введите имя:");
        String regFirstName = scanner.nextLine();
        System.out.println("Введите фамилию:");
        String regLastName = scanner.nextLine();
        System.out.println("Введите пароль:");
        String regPassword = scanner.nextLine();
        userController.createNewUser(regFirstName, regLastName, regEmail, regPassword);
    }
}
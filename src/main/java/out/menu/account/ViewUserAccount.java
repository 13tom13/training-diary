package out.menu.account;

import entities.dto.UserDTO;
import entities.model.User;
import in.controller.training.TrainingController;
import in.controller.training.TrainingStatisticsController;
import utils.Logger;
import out.menu.training.ViewTrainingAdded;
import out.menu.training.ViewTrainingEditing;
import out.menu.statistic.ViewTrainingStatistcs;
import out.menu.training.ViewTraining;

import java.util.Scanner;

/**
 * Класс ViewUserAccount представляет меню для управления аккаунтом пользователя.
 */
public class ViewUserAccount {
    private final ViewTraining viewTraining;
    private final ViewTrainingAdded viewTrainingAdded;
    private final ViewTrainingEditing viewTrainingEditing;
    private final ViewTrainingStatistcs viewTrainingStatistcs;
    private final Scanner scanner;
    private final UserDTO userDTO;
    private static final Logger logger = Logger.getInstance();

    /**
     * Конструктор класса ViewUserAccount.
     * @param trainingController Контроллер тренировок.
     * @param trainingStatisticsController Контроллер статистики тренировок.
     * @param userDTO Пользователь.
     * @param scanner Сканер для ввода данных.
     */
    public ViewUserAccount(TrainingController trainingController, TrainingStatisticsController trainingStatisticsController, UserDTO userDTO, Scanner scanner) {
        this.viewTrainingStatistcs = new ViewTrainingStatistcs(trainingStatisticsController, userDTO, scanner);
        this.viewTraining = new ViewTraining(trainingController);
        this.viewTrainingAdded = new ViewTrainingAdded(trainingController, userDTO, scanner);
        this.viewTrainingEditing = new ViewTrainingEditing(trainingController, viewTrainingAdded, userDTO, scanner);
        this.scanner = scanner;
        this.userDTO = userDTO;
    }

    /**
     * Метод для отображения меню аккаунта пользователя.
     */
    public void userAccountMenu() {
        boolean startAccount = true;
        while (startAccount) {
            System.out.printf("\nДобро пожаловать %s %s!\n", userDTO.getFirstName(), userDTO.getLastName());
            System.out.println("Выберите действие:");
            System.out.println("1. Просмотр всех тренировок");
            System.out.println("2. Добавление тренировки");
            System.out.println("3. Удаление тренировки");
            System.out.println("4. Внести изменение в тренировку");
            System.out.println("5. Статистика по тренировкам");
            System.out.println("6. Выход");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> viewTraining.viewAllTraining(userDTO);
                    case 2 -> viewTrainingAdded.addTraining();
                    case 3 -> viewTrainingAdded.deleteTraining();
                    case 4 -> viewTrainingEditing.editingTraining();
                    case 5 -> viewTrainingStatistcs.statisticMenu();
                    case 6 -> {
                        logger.logAction(userDTO.getEmail(), "logout");
                        System.out.println("Выход из аккаунта пользователя " + userDTO.getEmail());
                        startAccount = false;
                    }
                    default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
                }
            } else {
                System.out.println("Неверный выбор. Попробуйте еще раз.");
                scanner.nextLine();
            }
        }
    }
}



package out.menu.account;

import entities.dto.UserDTO;
import out.menu.training.ViewTrainingAdded.ViewTrainingAdded;
import out.menu.training.ViewTrainingAdded.implementation.ViewTrainingAddedByController;
import out.menu.training.viewTraining.ViewTraining;
import out.menu.training.viewTraining.implementation.ViewTrainingByHTTP;
import utils.Logger;
import out.menu.training.ViewTrainingEditing;
import out.menu.statistic.ViewTrainingStatistcs;

import java.util.Scanner;

/**
 * Класс ViewUserAccount представляет меню для управления аккаунтом пользователя.
 */
public class ViewUserAccount {
    private final ViewTraining viewTraining;
    private final ViewTrainingAdded viewTrainingAdded;
    private final ViewTrainingEditing viewTrainingEditing;
    private final ViewTrainingStatistcs viewTrainingStatistcs;
    private final Scanner scanner = new Scanner(System.in);
    private final UserDTO userDTO;
    private static final Logger logger = Logger.getInstance();

    /**
     * Конструктор класса ViewUserAccount.
     * @param userDTO Пользователь.
     */
    public ViewUserAccount(UserDTO userDTO) {
        this.userDTO = userDTO;
//        this.viewTrainingConsole = new ViewTrainingByController();
        this.viewTraining = new ViewTrainingByHTTP();
        this.viewTrainingAdded = new ViewTrainingAddedByController(userDTO);
        this.viewTrainingEditing = new ViewTrainingEditing(userDTO, viewTrainingAdded);
        this.viewTrainingStatistcs = new ViewTrainingStatistcs(userDTO);

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



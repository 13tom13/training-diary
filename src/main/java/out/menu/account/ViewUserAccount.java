package out.menu.account;

import entity.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import out.menu.statistic.ViewTrainingStatistics;
import out.menu.training.ViewTrainingAdded;
import out.menu.training.ViewTrainingEditing;
import out.menu.training.ViewTraining;
import utils.Logger;

import java.util.Scanner;

/**
 * Класс ViewUserAccount представляет меню для управления аккаунтом пользователя.
 */
@Component
public class ViewUserAccount {
    private final ViewTraining viewTraining;
    private final ViewTrainingAdded viewTrainingAdded;
    private final ViewTrainingEditing viewTrainingEditing;
    private final ViewTrainingStatistics viewTrainingStatistics;

    private final Scanner scanner = new Scanner(System.in);

    private static final Logger logger = Logger.getInstance();

    @Autowired
    public ViewUserAccount(ViewTraining viewTraining, ViewTrainingAdded viewTrainingAdded, ViewTrainingEditing viewTrainingEditing, ViewTrainingStatistics viewTrainingStatistics) {
        this.viewTraining = viewTraining;
        this.viewTrainingAdded = viewTrainingAdded;
        this.viewTrainingEditing = viewTrainingEditing;
        this.viewTrainingStatistics = viewTrainingStatistics;
    }

    /**
     * Метод для отображения меню аккаунта пользователя.
     */
    public void userAccountMenu(UserDTO userDTO) {
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
                    case 2 -> viewTrainingAdded.addTraining(userDTO);
                    case 3 -> viewTrainingAdded.deleteTraining(userDTO);
                    case 4 -> viewTrainingEditing.editingTraining(userDTO);
                    case 5 -> viewTrainingStatistics.statisticMenu(userDTO);
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



package out.menu.training;

import com.fasterxml.jackson.core.JsonProcessingException;
import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import out.messenger.TrainingHTTPMessenger;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.TreeSet;

import static utils.Utils.enterStringDate;
import static utils.Utils.getDateFromString;

/**
 * Представляет класс для редактирования тренировок пользователя.
 */
@Component
public class ViewTrainingEditing {

    private final TrainingHTTPMessenger trainingHTTPMessenger;

    private final ViewTrainingAdded viewTrainingAdded;

    private final Scanner scanner = new Scanner(System.in);

    private TrainingDTO trainingDTO;

    @Autowired
    public ViewTrainingEditing(TrainingHTTPMessenger trainingHTTPMessenger, ViewTrainingAdded viewTrainingAdded) {
        this.trainingHTTPMessenger = trainingHTTPMessenger;
        this.viewTrainingAdded = viewTrainingAdded;
    }

    /**
     * Получает тренировку для редактирования.
     */
    private void getTrainingForEditing(UserDTO userDTO) {
        TreeSet<TrainingDTO> trainingsFromDay;
        System.out.println("Введите дату тренировки: ");
        String trainingDate = enterStringDate(scanner);
        try {
            trainingsFromDay = trainingHTTPMessenger.getTrainingsByUserEmailAndData(userDTO, trainingDate);
        } catch (JsonProcessingException | RestClientException e) {
            System.err.println("Ошибка при получении тренировки для редактирования: " + e.getMessage());
            return;
        }
        if (!trainingsFromDay.isEmpty()) {
            System.out.println("Тренировки на " + trainingDate + ":");
            for (TrainingDTO training : trainingsFromDay) {
                System.out.println(training);
            }
            System.out.println("Введите название тренировки: ");
            String trainingName = scanner.nextLine();
            try {
                trainingDTO = trainingHTTPMessenger.getTrainingByUserEmailAndDateAndName(userDTO, trainingDate, trainingName);
            } catch (JsonProcessingException | RestClientException e) {
                System.err.println("Ошибка при получении тренировки для редактирования: " + e.getMessage());
            }
        }
    }


    /**
     * Редактирует тренировку.
     */
    public void editingTraining(UserDTO userDTO) {
        getTrainingForEditing(userDTO);
        boolean startView = false;
        if (trainingDTO != null && trainingDTO.getName() != null) {
            startView = true;
        } else {
            System.out.println("Тренировка не найдена.");
        }
        while (startView) {
            System.out.println();
            System.out.printf("Редактирование тренировки:\n%s", trainingDTO);
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
                        try {
                            trainingDTO = trainingHTTPMessenger.changeNameTraining(userDTO, trainingDTO, newName);
                        } catch (JsonProcessingException | RestClientException e) {
                            System.err.println("Ошибка изменении имени тренировки: " + e.getMessage());
                        }
                    }
                    case 2 -> {
                        System.out.println();
                        System.out.println("Введите новую дату:");
                        String stringDate = scanner.nextLine();

                        try {
                            LocalDate newDate = getDateFromString(stringDate);
                            trainingDTO = trainingHTTPMessenger.changeDateTraining(userDTO, trainingDTO, newDate);
                        } catch (JsonProcessingException | RestClientException e) {
                            System.err.println("Ошибка изменении даты тренировки: " + e.getMessage());
                        }
                    }
                    case 3 -> {
                        System.out.println();
                        System.out.println("Введите новую продолжительность:");
                        int newDuration = getPositiveNumber();
                        try {
                            trainingDTO = trainingHTTPMessenger.changeDurationTraining(userDTO, trainingDTO, newDuration);
                        } catch (JsonProcessingException | RestClientException e) {
                            System.err.println("Ошибка изменении продолжительности тренировки: " + e.getMessage());
                        }
                    }
                    case 4 -> {
                        System.out.println();
                        System.out.println("Введите новое количество сожженных калорий:");
                        int newCalories = getPositiveNumber();
                        try {
                            trainingDTO = trainingHTTPMessenger.changeCaloriesTraining(userDTO, trainingDTO, newCalories);
                        } catch (JsonProcessingException | RestClientException e) {
                            System.err.println("Ошибка изменении потраченных калорий тренировки: " + e.getMessage());
                        }
                    }
                    case 5 -> viewTrainingAdded.addTrainingAdditional(userDTO, trainingDTO);
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

    private int getPositiveNumber() {
        int number;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("Это не число. Пожалуйста, введите число:");
                scanner.next();
            }
            number = scanner.nextInt();
            if (number <= 0) {
                System.out.println("Число должно быть положительным. Пожалуйста, введите положительное число:");
            }
        } while (number <= 0);
        return number;
    }
}

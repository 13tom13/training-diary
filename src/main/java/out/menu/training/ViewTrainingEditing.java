package out.menu.training;

import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import exceptions.RepositoryException;
import in.controller.training.TrainingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.TreeSet;

import static utils.Utils.*;

/**
 * Представляет класс для редактирования тренировок пользователя.
 */
@Component
public class ViewTrainingEditing {
//
//    private final TrainingController trainingController;
//    private final ViewTrainingAdded viewTrainingAdded;
//    private UserDTO userDTO;
//    private final Scanner scanner = new Scanner(System.in);
//
//    private TrainingDTO trainingDTO;
//
//    /**
//     * Создает экземпляр ViewTrainingEditing с заданным контроллером тренировок, представлением добавления тренировок, пользователем и сканером.
//     *
//     * @param viewTrainingAdded Представление добавления тренировок.
//     */
//    @Autowired
//    public ViewTrainingEditing(TrainingController trainingController, ViewTrainingAdded viewTrainingAdded) {
//        this.trainingController = trainingController;
//        this.viewTrainingAdded = viewTrainingAdded;
//    }
//
//    public void setUserDTO(UserDTO userDTO) {
//        this.userDTO = userDTO;
//    }
//
//    /**
//     * Получает тренировку для редактирования.
//     */
//    private void getTrainingForEditing() {
//        TreeSet<TrainingDTO> trainingsFromDay;
//        System.out.println("Введите дату тренировки: ");
//        String trainingDate = enterStringDate(scanner);
//        trainingsFromDay = trainingController.getTrainingsByUserEmailAndData(userDTO, trainingDate);
//        if (!trainingsFromDay.isEmpty()) {
//            System.out.println("Тренировки на " + trainingDate + ":");
//            for (TrainingDTO training : trainingsFromDay) {
//                System.out.println(training);
//            }
//            System.out.println("Введите название тренировки: ");
//            String trainingName = scanner.nextLine();
//            try {
//                trainingDTO = trainingController.getTrainingByUserEmailAndDateAndName(userDTO, trainingDate, trainingName);
//            } catch (IOException e) {
//                System.err.println("Ошибка при получении тренировки для редактирования: " + e.getMessage());
//            }
//        }
//    }
//
//
//    /**
//     * Редактирует тренировку.
//     */
//    public void editingTraining() {
//        getTrainingForEditing();
//        boolean startView = false;
//        if (trainingDTO != null && trainingDTO.getName() != null) {
//            startView = true;
//        } else {
//            System.out.println("Тренировка не найдена.");
//        }
//        while (startView) {
//            System.out.println();
//            System.out.printf("Редактирование тренировки:\n%s", trainingDTO);
//            System.out.println();
//            System.out.println("Выберите действие:");
//            System.out.println("1. изменить название");
//            System.out.println("2. изменить дату тренировки");
//            System.out.println("3. изменить продолжительность");
//            System.out.println("4. изменить сожженные калории");
//            System.out.println("5. изменить дополнительную информацию");
//            System.out.println("6. Выход");
//            if (scanner.hasNextInt()) {
//                int choice = scanner.nextInt();
//                scanner.nextLine();
//                switch (choice) {
//                    case 1 -> {
//                        System.out.println();
//                        System.out.println("Введите новое название:");
//                        String newName = scanner.nextLine();
//                        try {
//                            trainingDTO = trainingController.changeNameTraining(userDTO, trainingDTO, newName);
//                        } catch (RepositoryException e) {
//                            System.err.println("Ошибка изменении имени тренировки: " + e.getMessage());
//                        }
//                    }
//                    case 2 -> {
//                        System.out.println();
//                        System.out.println("Введите новую дату:");
//                        String stringDate = scanner.nextLine();
//
//                        try {
//                            LocalDate newDate = getDateFromString(stringDate);
//                            trainingDTO = trainingController.changeDateTraining(userDTO, trainingDTO, newDate);
//                        } catch (DateTimeParseException e) {
//                            System.err.println("Неверный формат даты. Пожалуйста, введите дату в формате " + DATE_FORMAT);
//                        } catch (RepositoryException e) {
//                            System.err.println("Ошибка изменении даты тренировки: " + e.getMessage());
//                        }
//                    }
//                    case 3 -> {
//                        System.out.println();
//                        System.out.println("Введите новую продолжительность:");
//                        String newDuration = scanner.nextLine();
//                        try {
//                            trainingDTO = trainingController.changeDurationTraining(userDTO, trainingDTO, newDuration);
//                        } catch (RepositoryException e) {
//                            System.err.println("Ошибка изменении продолжительности тренировки: " + e.getMessage());
//                        }
//                    }
//                    case 4 -> {
//                        System.out.println();
//                        System.out.println("Введите новое количество сожженных калорий:");
//                        String newCalories = scanner.nextLine();
//                        try {
//                            trainingDTO = trainingController.changeCaloriesTraining(userDTO, trainingDTO, newCalories);
//                        } catch (RepositoryException e) {
//                            System.err.println("Ошибка изменении потраченных калорий тренировки: " + e.getMessage());
//                        }
//                    }
//                    case 5 -> viewTrainingAdded.addTrainingAdditional(trainingDTO);
//                    case 6 -> {
//                        System.out.println("Выход из меню изменения тренировки.");
//                        startView = false;
//                    }
//                    default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
//                }
//            } else {
//                System.out.println("Неверный ввод. Пожалуйста, введите число.");
//                scanner.nextLine();
//            }
//        }
//    }
}

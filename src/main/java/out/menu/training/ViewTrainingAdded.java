package out.menu.training;

import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoWriteRightsException;
import in.controller.training.TrainingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static utils.Utils.enterStringDate;
import static utils.Utils.getDateFromString;

/**
 * Представляет класс для добавления и удаления тренировок пользователя.
 */
@Component
public class ViewTrainingAdded {

//    private final TrainingController trainingController;
//    private UserDTO userDTO;
//    private final Scanner scanner = new Scanner(System.in);
//
//    /**
//     * Создает экземпляр ViewTrainingAdded с заданным контроллером тренировок, пользователем и сканером.
//     *
//     */
//    @Autowired
//    public ViewTrainingAdded(TrainingController trainingController) {
//        this.trainingController = trainingController;
//    }
//
//    public void setUserDTO(UserDTO userDTO) {
//        this.userDTO = userDTO;
//    }
//
//    /**
//     * Метод для добавления тренировки.
//     */
//    public void addTraining() {
//        System.out.println("Введите данные тренировки:");
//        String name;
//        try {
//            name = chooseTrainingType();
//        } catch (IOException e) {
//            System.err.println("Ошибка при получении списка типов тренировок: " + e.getMessage());
//            return;
//        }
//        String date = enterStringDate(scanner);
//        int duration = enterTrainingDuration();
//        int caloriesBurned = enterCaloriesBurned();
//        LocalDate convertedDate = getDateFromString(date);
//        TrainingDTO trainingDTO = new TrainingDTO(name, convertedDate, duration, caloriesBurned);
//        try {
//            TrainingDTO savedTraining = trainingController.saveTraining(userDTO, trainingDTO);
//            addTrainingAdditional(savedTraining);
//            System.out.println("Тренировка успешно сохранена.");
//        } catch (InvalidDateFormatException | NoWriteRightsException | RepositoryException e) {
//            System.err.println(e.getMessage());
//        }
//
//
//    }
//
//    /**
//     * Метод для удаления тренировки.
//     */
//    public void deleteTraining() {
//        System.out.print("Введите дату тренировки (дд.мм.гг): ");
//        String trainingDate = enterStringDate(scanner);
//        TreeSet<TrainingDTO> trainingsFromDay = trainingController.getTrainingsByUserEmailAndData(userDTO, trainingDate);
//        for (TrainingDTO training : trainingsFromDay) {
//            System.out.println(training);
//        }
//        System.out.print("Название: ");
//        String name = scanner.nextLine();
//        try {
//            trainingController.deleteTraining(userDTO, trainingDate, name);
//        } catch (NoDeleteRightsException e) {
//            System.err.println("нет прав на удаление: " + e.getMessage());
//        } catch (RepositoryException e) {
//            System.err.println("Ошибка при удалении тренировки: " + e.getMessage());
//        }
//        System.out.println("Тренировка успешно удалена.");
//
//    }
//
//    /**
//     * Метод для добавления дополнительной информации о тренировке.
//     */
//    public void addTrainingAdditional(TrainingDTO trainingDTO) {
//        boolean startAdd = true;
//        while (startAdd) {
//            System.out.println("1. Добавить дополнительную информацию?");
//            if (trainingDTO.getAdditions() != null && !trainingDTO.getAdditions().isEmpty()) {
//                System.out.println("2. Удалить дополнительную информацию?");
//            }
//            System.out.println("3. Выход");
//            if (scanner.hasNextInt()) {
//                int choice = scanner.nextInt();
//                scanner.nextLine();
//                switch (choice) {
//                    case 1 -> {
//                        System.out.println("Введите дополнительную информацию:");
//                        System.out.println("Название:");
//                        String additionalName = scanner.nextLine();
//                        System.out.println("Значение:");
//                        String additionalValue = scanner.nextLine();
//                        trainingDTO = trainingController.addTrainingAdditional(userDTO, trainingDTO, additionalName, additionalValue);
//                    }
//                    case 2 -> {
//                        if (!trainingDTO.getAdditions().isEmpty()) {
//                            for (Map.Entry<String, String> entry : trainingDTO.getAdditions().entrySet()) {
//                                System.out.println(entry.getKey() + " - " + entry.getValue());
//                            }
//                            System.out.println("Введите название дополнительной информации для удаления:");
//
//                            String additionalNameForRemove = scanner.nextLine();
//                            trainingDTO = trainingController.removeTrainingAdditional(userDTO, trainingDTO, additionalNameForRemove);
//                        }
//                    }
//                    case 3 -> {
//                        System.out.println("Создание тренировки завершено.");
//                        startAdd = false;
//                    }
//                    default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
//                }
//            } else {
//                System.out.println("Неверный ввод. Пожалуйста, введите число.");
//                scanner.nextLine();
//            }
//        }
//    }
//
//    /**
//     * Метод для выбора типа тренировки.
//     *
//     * @return Выбранный тип тренировки.
//     */
//    private String chooseTrainingType() throws IOException {
//        System.out.println("Выберите тип тренировки:");
//
//        List<String> trainingTypes = trainingController.getTrainingTypes(userDTO);
//
//        for (int i = 0; i < trainingTypes.size(); i++) {
//            System.out.println((i + 1) + ". " + trainingTypes.get(i));
//        }
//
//        System.out.println((trainingTypes.size() + 1) + ". Ввести свой тип тренировки");
//
//        while (true) {
//            try {
//                System.out.print("Ваш выбор: ");
//                int choice = scanner.nextInt();
//                if (choice >= 1 && choice <= trainingTypes.size()) {
//                    String selectedTrainingType = trainingTypes.get(choice - 1);
//                    System.out.println("Выбран тип тренировки: " + selectedTrainingType);
//                    scanner.nextLine(); // Очистка буфера
//                    return selectedTrainingType;
//                } else if (choice == trainingTypes.size() + 1) {
//                    scanner.nextLine(); // Очистка буфера
//                    System.out.print("Введите свой тип тренировки: ");
//                    String customTrainingType = scanner.nextLine();
//                    trainingController.saveTrainingType(userDTO, customTrainingType);
//                    System.out.println("Выбран пользовательский тип тренировки: " + customTrainingType);
//                    return customTrainingType;
//                } else {
//                    System.out.println("Некорректный выбор.");
//                }
//            } catch (InputMismatchException e) {
//                scanner.nextLine(); // Очистка буфера
//                System.out.println("Некорректный ввод. Пожалуйста, введите число.");
//            }
//        }
//
//    }
//
//    private int enterTrainingDuration() {
//        while (true) {
//            int duration;
//            try {
//                System.out.print("Продолжительность (минуты): ");
//                duration = Integer.parseInt(scanner.nextLine());
//                if (duration <= 0) {
//                    System.err.println("Продолжительность должна быть положительным числом.");
//                    continue;
//                }
//            } catch (NumberFormatException e) {
//                System.err.println("Неверный формат продолжительности. Пожалуйста, введите целое число.");
//                continue;
//            }
//            return duration;
//        }
//    }
//
//    private int enterCaloriesBurned() {
//        int caloriesBurned;
//        while (true) {
//            try {
//                System.out.print("Сожженные калории: ");
//                caloriesBurned = Integer.parseInt(scanner.nextLine());
//                if (caloriesBurned <= 0) {
//                    System.err.println("Сожженные калории должны быть положительным числом.");
//                    continue;
//                }
//            } catch (NumberFormatException e) {
//                System.err.println("Неверный формат сожженных калорий. Пожалуйста, введите целое число.");
//                continue;
//            }
//            return caloriesBurned;
//        }
//    }


}

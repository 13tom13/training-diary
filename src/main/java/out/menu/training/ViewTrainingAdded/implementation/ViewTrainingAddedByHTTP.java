package out.menu.training.ViewTrainingAdded.implementation;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.initializer.in.ControllerFactory;
import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoWriteRightsException;
import in.controller.training.TrainingController;
import out.menu.training.ViewTrainingAdded.ViewTrainingAdded;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static config.ApplicationConfig.getRootURL;
import static utils.HTTP.sendPostRequest;
import static utils.Utils.getDateFromString;

/**
 * Представляет класс для добавления и удаления тренировок пользователя.
 */
public class ViewTrainingAddedByHTTP implements ViewTrainingAdded {

    private final UserDTO userDTO;
    private final Scanner scanner = new Scanner(System.in);

    private final String rootURL = getRootURL();
    private final String saveTrainingServletPath = "/training/savetraining";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Создает экземпляр ViewTrainingAdded с заданным контроллером тренировок, пользователем и сканером.
     *
     * @param userDTO            Пользователь.
     */
    public ViewTrainingAddedByHTTP(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    /**
     * Метод для добавления тренировки.
     */
    @Override
    public void addTraining() {
        System.out.println("Введите данные тренировки:");
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

        TrainingDTO trainingDTO = new TrainingDTO(name, date, duration, caloriesBurned);
        try {
            String requestURL = rootURL + saveTrainingServletPath;
            String savedTraining = sendPostRequest(requestURL, trainingDTO.toString());
            trainingDTO = objectMapper.readValue(savedTraining, TrainingDTO.class);
            addTrainingAdditional(trainingDTO);
            System.out.println("Тренировка успешно сохранена.");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Метод для выбора типа тренировки.
     *
     * @return Выбранный тип тренировки.
     */
    @Override
    public String chooseTrainingType() {
        System.out.println("Выберите тип тренировки:");

        List<String> trainingTypes = null;
//                trainingController.getTrainingTypes(userDTO);

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
//                    trainingController.saveTrainingType(userDTO, customTrainingType);
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
    @Override
    public void deleteTraining() {
        System.out.print("Введите дату тренировки (дд.мм.гг): ");
        String stringDate = scanner.nextLine();
        Date trainingDate;
        try {
            trainingDate = getDateFromString(stringDate);
        } catch (ParseException e) {
            System.err.println("Неверный формат даты. Пожалуйста, введите дату в формате дд.мм.гг.");
            return; // Выйти из метода, если дата некорректна
        }
        TreeSet<TrainingDTO> trainingsFromDay = null;
//                trainingController.getTrainingsByUserEmailAndData(userDTO, trainingDate);
        for (TrainingDTO training : trainingsFromDay) {
            System.out.println(training);
        }
        System.out.print("Название: ");
        String name = scanner.nextLine();
//        if (trainingController.deleteTraining(userDTO, trainingDate, name)) {
//            System.out.println("Тренировка успешно удалена.");
//        }
    }

    /**
     * Метод для добавления дополнительной информации о тренировке.
     */
    @Override
    public void addTrainingAdditional(TrainingDTO trainingDTO) {
        boolean startAdd = true;
        while (startAdd) {
            System.out.println("1. Добавить дополнительную информацию?");
            if (trainingDTO.getAdditions() != null && !trainingDTO.getAdditions().isEmpty()) {
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
//                        trainingDTO = trainingController.addTrainingAdditional(userDTO, trainingDTO, additionalName, additionalValue);
                    }
                    case 2 -> {
                        if (!trainingDTO.getAdditions().isEmpty()) {
                            for (Map.Entry<String, String> entry : trainingDTO.getAdditions().entrySet()) {
                                System.out.println(entry.getKey() + " - " + entry.getValue());
                            }
                            System.out.println("Введите название дополнительной информации для удаления:");

                            String additionalNameForRemove = scanner.nextLine();
//                            trainingDTO = trainingController.removeTrainingAdditional(userDTO, trainingDTO, additionalNameForRemove);
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

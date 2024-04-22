package in.controller.training.implementation;

import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;
import in.controller.training.TrainingController;
import in.service.training.TrainingService;
import model.Training;
import model.User;
import utils.Logger;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Реализация интерфейса {@link TrainingController}, предоставляющая методы управления тренировками.
 */
public class TrainingControllerImpl implements TrainingController {

    private final TrainingService trainingService; // Сервис для работы с тренировками
    private final Logger logger; // Логгер для записи действий и ошибок

    /**
     * Конструктор класса.
     *
     * @param trainingService Сервис для работы с тренировками
     */
    public TrainingControllerImpl(TrainingService trainingService) {
        this.trainingService = trainingService;
        logger = Logger.getInstance(); // Получаем экземпляр логгера
    }

    /**
     * Получает все тренировки пользователя.
     *
     * @param user Пользователь
     * @return Словарь, содержащий тренировки, сгруппированные по дате
     */
    @Override
    public TreeMap<Date, TreeSet<Training>> getAllTrainings(User user) {
        return trainingService.getAllTrainings(user);
    }

    /**
     * Получает список типов тренировок для пользователя.
     *
     * @param user Пользователь
     * @return Список типов тренировок
     */
    @Override
    public List<String> getTrainingTypes(User user) {
        String userEmail = user.getEmail();
        return trainingService.getTrainingTypes(userEmail);
    }


    /**
     * Сохраняет тренировку пользователя.
     *
     * @param user     Пользователь
     * @param training Тренировка для сохранения
     * @return {@code true}, если тренировка сохранена успешно, в противном случае {@code false}
     */
    @Override
    public boolean saveTraining(User user, Training training) {
        try {
            trainingService.saveTraining(user, training);
            logger.logAction(user.getEmail(), "save training " + training.getName() + " " + training.getDate());
            return true;
        } catch (RepositoryException | InvalidDateFormatException | NoWriteRightsException e) {
            logger.logError(user.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Сохраняет пользовательский тип тренировки.
     *
     * @param user               Пользователь
     * @param customTrainingType Новый пользовательский тип тренировки
     */
    @Override
    public void saveTrainingType(User user, String customTrainingType) {
        trainingService.saveTrainingType(user, customTrainingType);
    }

    /**
     * Удаляет тренировку пользователя.
     *
     * @param user Пользователь
     * @param date Дата тренировки
     * @param name Название тренировки
     * @return
     */
    @Override
    public boolean deleteTraining(User user, Date date, String name) {
        try {
            boolean result = trainingService.deleteTraining(user, date, name);
            logger.logAction(user.getEmail(), "delete training " + name + " " + date);
            return result;
        } catch (RepositoryException | InvalidDateFormatException | NoDeleteRightsException e) {
            logger.logError(user.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Получает все тренировки пользователя на определенную дату.
     *
     * @param user         Пользователь
     * @param trainingDate Дата тренировки
     * @return Множество тренировок пользователя на указанную дату
     */
    @Override
    public TreeSet<Training> getTrainingsByUserEmailAndData(User user, Date trainingDate) {
        try {
            return trainingService.getTrainingsByUserIDAndData(user, trainingDate);
        } catch (RepositoryException e) {
            logger.logError(user.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
            return new TreeSet<>();
        }
    }

    /**
     * Получает тренировку пользователя по указанной дате и названию.
     *
     * @param user         Пользователь
     * @param trainingDate Дата тренировки
     * @param trainingName Название тренировки
     * @return Тренировка пользователя по указанной дате и названию
     */
    @Override
    public Training getTrainingByUserEmailAndDataAndName(User user, Date trainingDate, String trainingName) {
        try {
            return trainingService.getTrainingByUserIDAndDataAndName(user, trainingDate, trainingName);
        } catch (RepositoryException e) {
            logger.logError(user.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
            return new Training();
        }
    }

    /**
     * Добавляет дополнительную информацию к тренировке.
     *
     * @param user            Пользователь
     * @param training        Тренировка, к которой добавляется информация
     * @param additionalName  Название дополнительной информации
     * @param additionalValue Значение дополнительной информации
     */
    @Override
    public void addTrainingAdditional(User user, Training training, String additionalName, String additionalValue) {
        try {
            trainingService.addTrainingAdditional(user, training, additionalName, additionalValue);
            logger.logAction(user.getEmail(), String.format("add training additional %s %s (%s %s)",
                    additionalName, additionalValue, training.getName(), training.getDate()));
        } catch (RepositoryException | NoWriteRightsException e) {
            logger.logError(user.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
        }
    }

    /**
     * Удаляет дополнительную информацию из тренировки.
     *
     * @param user           Пользователь
     * @param training       Тренировка, из которой удаляется информация
     * @param additionalName Название дополнительной информации для удаления
     */
    @Override
    public void removeTrainingAdditional(User user, Training training, String additionalName) {
        try {
            trainingService.removeTrainingAdditional(user, training, additionalName);
            logger.logAction(user.getEmail(), String.format("remove training additional %s (%s %s)",
                    additionalName, training.getName(), training.getDate()));
        } catch (RepositoryException | NoEditRightsException e) {
            logger.logError(user.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
        }
    }

    /**
     * Изменяет название тренировки.
     *
     * @param user     Пользователь
     * @param training Тренировка, название которой изменяется
     * @param newName  Новое название тренировки
     */
    @Override
    public void changeNameTraining(User user, Training training, String newName) {
        try {
            trainingService.changeNameTraining(user, training, newName);
            logger.logAction(user.getEmail(),
                    String.format("change training name %s (%s %s)", newName, training.getName(), training.getDate()));
        } catch (RepositoryException | NoEditRightsException e) {
            logger.logError(user.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
        }
    }

    /**
     * Изменяет дату тренировки.
     *
     * @param user     Пользователь
     * @param training Тренировка, дата которой изменяется
     * @param newDate  Новая дата тренировки
     */
    @Override
    public void changeDateTraining(User user, Training training, Date newDate) {
        try {
            trainingService.changeDateTraining(user, training, newDate);
            logger.logAction(user.getEmail(),
                    String.format("change training date %s (%s %s)", newDate, training.getName(), training.getDate()));
        } catch (RepositoryException | InvalidDateFormatException | NoEditRightsException e) {
            logger.logError(user.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
        }
    }

    /**
     * Изменяет продолжительность тренировки.
     *
     * @param user        Пользователь
     * @param training    Тренировка, продолжительность которой изменяется
     * @param newDuration Новая продолжительность тренировки
     */
    @Override
    public void changeDurationTraining(User user, Training training, String newDuration) {
        int newDurationInt = Integer.parseInt(newDuration);
        try {
            trainingService.changeDurationTraining(user, training, newDurationInt);
            logger.logAction(user.getEmail(),
                    String.format("change training duration %s (%s %s)", newDuration, training.getName(), training.getDate()));
        } catch (RepositoryException | NoEditRightsException e) {
            logger.logError(user.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
        }
    }

    /**
     * Изменяет количество сожженных калорий на тренировке.
     *
     * @param user        Пользователь
     * @param training    Тренировка, количество калорий на которой изменяется
     * @param newCalories Новое количество сожженных калорий
     */
    @Override
    public void changeCaloriesTraining(User user, Training training, String newCalories) {
        int newCaloriesInt = Integer.parseInt(newCalories);
        try {
            trainingService.changeCaloriesTraining(user, training, newCaloriesInt);
            logger.logAction(user.getEmail(),
                    String.format("change training calories %s (%s %s)", newCaloriesInt, training.getName(), training.getDate()));
        } catch (RepositoryException | NoEditRightsException e) {
            logger.logError(user.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
        }
    }
}

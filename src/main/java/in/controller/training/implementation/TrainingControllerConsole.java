package in.controller.training.implementation;

import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;
import in.controller.training.TrainingController;
import in.service.training.TrainingService;
import utils.Logger;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Реализация интерфейса {@link TrainingController}, предоставляющая методы управления тренировками.
 */
public class TrainingControllerConsole implements TrainingController {

    private final TrainingService trainingService; // Сервис для работы с тренировками
    private final Logger logger; // Логгер для записи действий и ошибок

    /**
     * Конструктор класса.
     *
     * @param trainingService Сервис для работы с тренировками
     */
    public TrainingControllerConsole(TrainingService trainingService) {
        this.trainingService = trainingService;
        logger = Logger.getInstance(); // Получаем экземпляр логгера
    }

    /**
     * Получает все тренировки пользователя.
     *
     * @param userDTO Пользователь
     * @return Словарь, содержащий тренировки, сгруппированные по дате
     */
    @Override
    public TreeMap<Date, TreeSet<TrainingDTO>> getAllTrainings(UserDTO userDTO) {
        return trainingService.getAllTrainings(userDTO);
    }

    /**
     * Получает список типов тренировок для пользователя.
     *
     * @param userDTO Пользователь
     * @return Список типов тренировок
     */
    @Override
    public List<String> getTrainingTypes(UserDTO userDTO) {
        return trainingService.getTrainingTypes(userDTO);
    }


    /**
     * Сохраняет тренировку пользователя.
     *
     * @param userDTO     Пользователь
     * @param trainingDTO Тренировка для сохранения
     * @return {@code true}, если тренировка сохранена успешно, в противном случае {@code false}
     */
    @Override
    public TrainingDTO saveTraining(UserDTO userDTO, TrainingDTO trainingDTO) throws InvalidDateFormatException, NoWriteRightsException, RepositoryException {
            trainingDTO = trainingService.saveTraining(userDTO, trainingDTO);
            logger.logAction(userDTO.getEmail(), "save training " + trainingDTO.getName() + " " + trainingDTO.getDate());
            return trainingDTO;
    }

    /**
     * Сохраняет пользовательский тип тренировки.
     *
     * @param userDTO               Пользователь
     * @param customTrainingType Новый пользовательский тип тренировки
     */
    @Override
    public void saveTrainingType(UserDTO userDTO, String customTrainingType) {
        trainingService.saveTrainingType(userDTO, customTrainingType);
    }

    /**
     * Удаляет тренировку пользователя.
     *
     * @param userDTO Пользователь
     * @param date Дата тренировки
     * @param name Название тренировки
     * @return {@code true}, если тренировка удалена успешно, в противном случае {@code false}
     */
    @Override
    public boolean deleteTraining(UserDTO userDTO, Date date, String name) {
        try {
            boolean result = trainingService.deleteTraining(userDTO, date, name);
            logger.logAction(userDTO.getEmail(), "delete training " + name + " " + date);
            return result;
        } catch (RepositoryException | InvalidDateFormatException | NoDeleteRightsException e) {
            logger.logError(userDTO.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Получает все тренировки пользователя на определенную дату.
     *
     * @param userDTO      Пользователь
     * @param trainingDate Дата тренировки
     * @return Множество тренировок пользователя на указанную дату
     */
    @Override
    public TreeSet<TrainingDTO> getTrainingsByUserEmailAndData(UserDTO userDTO, Date trainingDate) {
        try {
            return trainingService.getTrainingsByUserEmailAndData(userDTO, trainingDate);
        } catch (RepositoryException e) {
            logger.logError(userDTO.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
            return new TreeSet<>();
        }
    }

    /**
     * Получает тренировку пользователя по указанной дате и названию.
     *
     * @param userDTO      Пользователь
     * @param trainingDate Дата тренировки
     * @param trainingName Название тренировки
     * @return Тренировка пользователя по указанной дате и названию
     */
    @Override
    public TrainingDTO getTrainingByUserEmailAndDateAndName(UserDTO userDTO, Date trainingDate, String trainingName) {
        try {
            return trainingService.getTrainingByUserEmailAndDataAndName(userDTO, trainingDate, trainingName);
        } catch (RepositoryException e) {
            logger.logError(userDTO.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
            return new TrainingDTO();
        }
    }

    /**
     * Добавляет дополнительную информацию к тренировке.
     *
     * @param userDTO         Пользователь
     * @param trainingDTO     Тренировка, к которой добавляется информация
     * @param additionalName  Название дополнительной информации
     * @param additionalValue Значение дополнительной информации
     * @return Тренировка, с добавленной дополнительной информацией
     */
    @Override
    public TrainingDTO addTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName, String additionalValue) {
        try {
            trainingDTO = trainingService.addTrainingAdditional(userDTO, trainingDTO, additionalName, additionalValue);
            logger.logAction(userDTO.getEmail(), String.format("add training additional %s %s (%s %s)",
                    additionalName, additionalValue, trainingDTO.getName(), trainingDTO.getDate()));
        } catch (RepositoryException | NoWriteRightsException e) {
            logger.logError(userDTO.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
        }
        return trainingDTO;
    }

    /**
     * Удаляет дополнительную информацию из тренировки.
     *
     * @param userDTO        Пользователь
     * @param trainingDTO    Тренировка, из которой удаляется информация
     * @param additionalName Название дополнительной информации для удаления
     * @return Тренировка, с удаленной информацией
     */
    @Override
    public TrainingDTO removeTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName) {
        try {
            trainingDTO = trainingService.removeTrainingAdditional(userDTO, trainingDTO, additionalName);
            logger.logAction(userDTO.getEmail(), String.format("remove training additional %s (%s %s)",
                    additionalName, trainingDTO.getName(), trainingDTO.getDate()));
        } catch (RepositoryException | NoEditRightsException e) {
            logger.logError(userDTO.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
        }
        return trainingDTO;
    }

    /**
     * Изменяет название тренировки.
     *
     * @param userDTO     Пользователь
     * @param trainingDTO Тренировка, название которой изменяется
     * @param newName     Новое название тренировки
     * @return Тренировка, с измененным названием
     */
    @Override
    public TrainingDTO changeNameTraining(UserDTO userDTO, TrainingDTO trainingDTO, String newName) {
        try {
            trainingDTO = trainingService.changeNameTraining(userDTO, trainingDTO, newName);

            logger.logAction(userDTO.getEmail(),
                    String.format("change training name %s (%s %s)", newName, trainingDTO.getName(), trainingDTO.getDate()));
        } catch (RepositoryException | NoEditRightsException e) {
            logger.logError(userDTO.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
        }
        return trainingDTO;
    }

    /**
     * Изменяет дату тренировки.
     *
     * @param userDTO     Пользователь
     * @param trainingDTO Тренировка, дата которой изменяется
     * @param newDate     Новая дата тренировки
     * @return Тренировка, с измененной датой
     */
    @Override
    public TrainingDTO changeDateTraining(UserDTO userDTO, TrainingDTO trainingDTO, Date newDate) {
        try {
            trainingDTO = trainingService.changeDateTraining(userDTO, trainingDTO, newDate);
            logger.logAction(userDTO.getEmail(),
                    String.format("change training date %s (%s %s)", newDate, trainingDTO.getName(), trainingDTO.getDate()));
        } catch (RepositoryException | InvalidDateFormatException | NoEditRightsException e) {
            logger.logError(userDTO.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
        }
        return trainingDTO;
    }

    /**
     * Изменяет продолжительность тренировки.
     *
     * @param userDTO     Пользователь
     * @param trainingDTO Тренировка, продолжительность которой изменяется
     * @param newDuration Новая продолжительность тренировки
     * @return Тренировка, с измененной продолжительностью
     */
    @Override
    public TrainingDTO changeDurationTraining(UserDTO userDTO, TrainingDTO trainingDTO, String newDuration) {
        int newDurationInt = Integer.parseInt(newDuration);
        try {
            trainingDTO = trainingService.changeDurationTraining(userDTO, trainingDTO, newDurationInt);
            logger.logAction(userDTO.getEmail(),
                    String.format("change training duration %s (%s %s)", newDuration, trainingDTO.getName(), trainingDTO.getDate()));
        } catch (RepositoryException | NoEditRightsException e) {
            logger.logError(userDTO.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
        }
        return trainingDTO;
    }

    /**
     * Изменяет количество сожженных калорий на тренировке.
     *
     * @param userDTO     Пользователь
     * @param trainingDTO Тренировка, количество калорий на которой изменяется
     * @param newCalories Новое количество сожженных калорий
     * @return Тренировка, с измененным количеством сожженных калорий
     */
    @Override
    public TrainingDTO changeCaloriesTraining(UserDTO userDTO, TrainingDTO trainingDTO, String newCalories) {
        int newCaloriesInt = Integer.parseInt(newCalories);
        try {
            trainingDTO = trainingService.changeCaloriesTraining(userDTO, trainingDTO, newCaloriesInt);
            logger.logAction(userDTO.getEmail(),
                    String.format("change training calories %s (%s %s)", newCaloriesInt, trainingDTO.getName(), trainingDTO.getDate()));
        } catch (RepositoryException | NoEditRightsException e) {
            logger.logError(userDTO.getEmail(), e.getMessage());
            System.err.println(e.getMessage());
        }
        return trainingDTO;
    }
}

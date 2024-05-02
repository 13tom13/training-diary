package in.controller.training;

import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoWriteRightsException;


import java.time.LocalDate;
import java.util.Date;

/**
 * Интерфейс, определяющий методы для модификации тренировок.
 */
public interface TrainingModificationController {

    /**
     * Сохраняет новую тренировку для пользователя.
     *
     * @param userDTO     пользователь, для которого сохраняется тренировка
     * @param trainingDTO тренировка, которую нужно сохранить
     * @return true, если тренировка успешно сохранена, в противном случае false
     */
    TrainingDTO saveTraining(UserDTO userDTO, TrainingDTO trainingDTO) throws InvalidDateFormatException, NoWriteRightsException, RepositoryException;

    /**
     * Удаляет тренировку пользователя по указанной дате и имени.
     *
     * @param userDTO пользователь, для которого удаляется тренировка
     * @param date дата тренировки
     * @param name имя тренировки
     * @return true, если тренировка успешно удалена, в противном случае false
     */
    boolean deleteTraining(UserDTO userDTO, LocalDate date, String name);

    /**
     * Добавляет дополнительную информацию к тренировке.
     *
     * @param userDTO         пользователь, для которого добавляется информация
     * @param trainingDTO     тренировка, к которой добавляется информация
     * @param additionalName  название дополнительной информации
     * @param additionalValue значение дополнительной информации
     * @return измененная тренировка
     */
    TrainingDTO addTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName, String additionalValue);

    /**
     * Удаляет дополнительную информацию из тренировки.
     *
     * @param userDTO        пользователь, для которого удаляется информация
     * @param trainingDTO    тренировка, из которой удаляется информация
     * @param additionalName название дополнительной информации
     * @return измененная тренировка
     */
    TrainingDTO removeTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName);

    /**
     * Изменяет имя тренировки.
     *
     * @param userDTO     пользователь, для которого изменяется имя тренировки
     * @param trainingDTO тренировка, у которой изменяется имя
     * @param newName     новое имя тренировки
     * @return измененная тренировка
     */
    TrainingDTO changeNameTraining(UserDTO userDTO, TrainingDTO trainingDTO, String newName);

    /**
     * Изменяет дату тренировки.
     *
     * @param userDTO     пользователь, для которого изменяется дата тренировки
     * @param trainingDTO тренировка, у которой изменяется дата
     * @param newDate     новая дата тренировки
     * @return измененная тренировка
     */
    TrainingDTO changeDateTraining(UserDTO userDTO, TrainingDTO trainingDTO, LocalDate newDate);

    /**
     * Изменяет продолжительность тренировки.
     *
     * @param userDTO     пользователь, для которого изменяется продолжительность тренировки
     * @param trainingDTO тренировка, у которой изменяется продолжительность
     * @param newDuration новая продолжительность тренировки
     * @return измененная тренировка
     */
    TrainingDTO changeDurationTraining(UserDTO userDTO, TrainingDTO trainingDTO, String newDuration);

    /**
     * Изменяет количество сожженных калорий на тренировке.
     *
     * @param userDTO     пользователь, для которого изменяется количество калорий на тренировке
     * @param trainingDTO тренировка, у которой изменяется количество калорий
     * @param newCalories новое количество сожженных калорий
     * @return измененная тренировка
     */
    TrainingDTO changeCaloriesTraining(UserDTO userDTO, TrainingDTO trainingDTO, String newCalories);
}
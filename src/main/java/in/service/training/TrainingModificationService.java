package in.service.training;

import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;

import java.time.LocalDate;
import java.util.Date;

/**
 * Интерфейс для модификации данных о тренировках.
 */
public interface TrainingModificationService {

    /**
     * Сохраняет новую тренировку для указанного пользователя.
     *
     * @param userDTO     пользователь, для которого сохраняется тренировка
     * @param trainingDTO новая тренировка
     * @return
     * @throws RepositoryException        если возникла ошибка доступа к хранилищу
     * @throws InvalidDateFormatException если указана некорректная дата тренировки
     * @throws NoWriteRightsException     если у пользователя нет прав на запись
     */
    TrainingDTO saveTraining(UserDTO userDTO, TrainingDTO trainingDTO) throws RepositoryException, InvalidDateFormatException, NoWriteRightsException;

    /**
     * Удаляет тренировку для указанного пользователя по указанной дате и имени.
     *
     * @param userDTO пользователь, для которого удаляется тренировка
     * @param date дата тренировки
     * @param name название тренировки
     * @return true, если тренировка успешно удалена, в противном случае false
     * @throws RepositoryException        если возникла ошибка доступа к хранилищу
     * @throws InvalidDateFormatException если указана некорректная дата тренировки
     * @throws SecurityException          если возникла ошибка безопасности
     * @throws NoDeleteRightsException    если у пользователя нет прав на удаление
     */
    boolean deleteTraining(UserDTO userDTO, LocalDate date, String name) throws RepositoryException, InvalidDateFormatException, SecurityException, NoDeleteRightsException;

    /**
     * Добавляет дополнительную информацию к тренировке.
     *
     * @param userDTO         пользователь, для которого добавляется дополнительная информация
     * @param trainingDTO     тренировка, к которой добавляется информация
     * @param additionalName  имя дополнительной информации
     * @param additionalValue значение дополнительной информации
     * @return измененная тренировка
     * @throws RepositoryException    если возникла ошибка доступа к хранилищу
     * @throws SecurityException      если возникла ошибка безопасности
     * @throws NoWriteRightsException если у пользователя нет прав на запись
     */
    TrainingDTO addTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName, String additionalValue) throws RepositoryException, SecurityException, NoWriteRightsException;

    /**
     * Удаляет дополнительную информацию из тренировки.
     *
     * @param userDTO        пользователь, для которого удаляется дополнительная информация
     * @param trainingDTO    тренировка, из которой удаляется информация
     * @param additionalName имя дополнительной информации
     * @return измененная тренировка
     * @throws RepositoryException   если возникла ошибка доступа к хранилищу
     * @throws SecurityException     если возникла ошибка безопасности
     * @throws NoEditRightsException если у пользователя нет прав на редактирование
     */
    TrainingDTO removeTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName) throws RepositoryException, SecurityException, NoEditRightsException;

    /**
     * Изменяет название тренировки.
     *
     * @param userDTO     пользователь, для которого изменяется название тренировки
     * @param trainingDTO тренировка, у которой изменяется название
     * @param newName     новое название тренировки
     * @return измененная тренировка
     * @throws RepositoryException   если возникла ошибка доступа к хранилищу
     * @throws SecurityException     если возникла ошибка безопасности
     * @throws NoEditRightsException если у пользователя нет прав на редактирование
     */
    TrainingDTO changeNameTraining(UserDTO userDTO, TrainingDTO trainingDTO, String newName) throws RepositoryException, SecurityException, NoEditRightsException;

    /**
     * Изменяет дату тренировки.
     *
     * @param userDTO     пользователь, для которого изменяется дата тренировки
     * @param trainingDTO тренировка, у которой изменяется дата
     * @param newDate     новая дата тренировки
     * @return измененная тренировка
     * @throws RepositoryException        если возникла ошибка доступа к хранилищу
     * @throws InvalidDateFormatException если указана некорректная дата тренировки
     * @throws SecurityException          если возникла ошибка безопасности
     * @throws NoEditRightsException      если у пользователя нет прав на редактирование
     */
    TrainingDTO changeDateTraining(UserDTO userDTO, TrainingDTO trainingDTO, LocalDate newDate) throws RepositoryException, InvalidDateFormatException, NoEditRightsException;

    /**
     * Изменяет длительность тренировки.
     *
     * @param userDTO     пользователь, для которого изменяется длительность тренировки
     * @param trainingDTO тренировка, у которой изменяется длительность
     * @param newDuration новая длительность тренировки
     * @return измененная тренировка
     * @throws RepositoryException   если возникла ошибка доступа к хранилищу
     * @throws NoEditRightsException если у пользователя нет прав на редактирование
     */
    TrainingDTO changeDurationTraining(UserDTO userDTO, TrainingDTO trainingDTO, int newDuration) throws RepositoryException, NoEditRightsException;

    /**
     * Изменяет количество сожженных калорий на тренировке.
     *
     * @param userDTO     пользователь, для которого изменяется количество сожженных калорий
     * @param trainingDTO тренировка, у которой изменяется количество сожженных калорий
     * @param newCalories новое количество сожженных калорий
     * @return измененная тренировка
     * @throws RepositoryException   если возникла ошибка доступа к хранилищу
     * @throws NoEditRightsException если у пользователя нет прав на редактирование
     */
    TrainingDTO changeCaloriesTraining(UserDTO userDTO, TrainingDTO trainingDTO, int newCalories) throws RepositoryException, NoEditRightsException;
}
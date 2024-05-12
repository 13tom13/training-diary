package in.service.training;

import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;

import java.time.LocalDate;

/**
 * Интерфейс для модификации данных о тренировках.
 */
public interface TrainingModificationService {

    /**
     * Сохраняет новую тренировку для указанного пользователя.
     *
     * @param userDTO     пользователь, для которого сохраняется тренировка
     * @param trainingDTO новая тренировка
     * @return новая тренировка
     * @throws RepositoryException        если возникла ошибка доступа к хранилищу
     * @throws NoWriteRightsException     если у пользователя нет прав на запись
     */
    TrainingDTO saveTraining(UserDTO userDTO, TrainingDTO trainingDTO) throws RepositoryException, NoWriteRightsException;

    /**
     * Удаляет тренировку для указанного пользователя по указанной дате и имени.
     *
     * @param email пользователь, для которого удаляется тренировка
     * @param date  дата тренировки
     * @param name  название тренировки
     * @throws RepositoryException     если возникла ошибка доступа к хранилищу
     * @throws NoDeleteRightsException если у пользователя нет прав на удаление
     */
    void deleteTraining(String email, String date, String name) throws RepositoryException, NoDeleteRightsException;

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
     * @param email          пользователь, для которого удаляется дополнительная информация
     * @param trainingDTO    тренировка, из которой удаляется информация
     * @param additionalName имя дополнительной информации
     * @return измененная тренировка
     * @throws RepositoryException   если возникла ошибка доступа к хранилищу
     * @throws SecurityException     если возникла ошибка безопасности
     * @throws NoEditRightsException если у пользователя нет прав на редактирование
     */
    TrainingDTO removeTrainingAdditional(String email, TrainingDTO trainingDTO, String additionalName) throws RepositoryException, SecurityException, NoEditRightsException;

    /**
     * Изменяет название тренировки.
     *
     * @param trainingDTO тренировка, у которой изменяется название
     * @param email       пользователь, для которого изменяется название тренировки
     * @param newName     новое название тренировки
     * @return измененная тренировка
     * @throws RepositoryException   если возникла ошибка доступа к хранилищу
     * @throws SecurityException     если возникла ошибка безопасности
     * @throws NoEditRightsException если у пользователя нет прав на редактирование
     */
    TrainingDTO changeNameTraining(TrainingDTO trainingDTO, String email, String newName) throws RepositoryException, SecurityException, NoEditRightsException;

    /**
     * Изменяет дату тренировки.
     *
     * @param trainingDTO тренировка, у которой изменяется дата
     * @param email       пользователь, для которого изменяется дата тренировки
     * @param newDate     новая дата тренировки
     * @return измененная тренировка
     * @throws RepositoryException        если возникла ошибка доступа к хранилищу
     * @throws InvalidDateFormatException если указана некорректная дата тренировки
     * @throws SecurityException          если возникла ошибка безопасности
     * @throws NoEditRightsException      если у пользователя нет прав на редактирование
     */
    TrainingDTO changeDateTraining(TrainingDTO trainingDTO, String email, LocalDate newDate) throws RepositoryException, InvalidDateFormatException, NoEditRightsException;

    /**
     * Изменяет длительность тренировки.
     *
     * @param trainingDTO тренировка, у которой изменяется длительность
     * @param email       пользователь, для которого изменяется длительность тренировки
     * @param newDuration новая длительность тренировки
     * @return измененная тренировка
     * @throws RepositoryException   если возникла ошибка доступа к хранилищу
     * @throws NoEditRightsException если у пользователя нет прав на редактирование
     */
    TrainingDTO changeDurationTraining(TrainingDTO trainingDTO, String email, int newDuration) throws RepositoryException, NoEditRightsException;

    /**
     * Изменяет количество сожженных калорий на тренировке.
     *
     * @param trainingDTO тренировка, у которой изменяется количество сожженных калорий
     * @param email       пользователь, для которого изменяется количество сожженных калорий
     * @param newCalories новое количество сожженных калорий
     * @return измененная тренировка
     * @throws RepositoryException   если возникла ошибка доступа к хранилищу
     * @throws NoEditRightsException если у пользователя нет прав на редактирование
     */
    TrainingDTO changeCaloriesTraining(TrainingDTO trainingDTO, String email, int newCalories) throws RepositoryException, NoEditRightsException;
}
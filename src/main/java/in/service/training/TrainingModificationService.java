package in.service.training;

import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;
import model.Training;
import model.User;

/**
 * Интерфейс для модификации данных о тренировках.
 */
public interface TrainingModificationService {

    /**
     * Сохраняет новую тренировку для указанного пользователя.
     *
     * @param user     пользователь, для которого сохраняется тренировка
     * @param training новая тренировка
     * @throws RepositoryException          если возникла ошибка доступа к хранилищу
     * @throws InvalidDateFormatException  если указана некорректная дата тренировки
     * @throws NoWriteRightsException      если у пользователя нет прав на запись
     */
    void saveTraining(User user, Training training) throws RepositoryException, InvalidDateFormatException, NoWriteRightsException;

    /**
     * Удаляет тренировку для указанного пользователя по указанной дате и имени.
     *
     * @param user пользователь, для которого удаляется тренировка
     * @param date дата тренировки
     * @param name название тренировки
     * @return
     * @throws RepositoryException        если возникла ошибка доступа к хранилищу
     * @throws InvalidDateFormatException если указана некорректная дата тренировки
     * @throws SecurityException          если возникла ошибка безопасности
     * @throws NoDeleteRightsException    если у пользователя нет прав на удаление
     */
    boolean deleteTraining(User user, String date, String name) throws RepositoryException, InvalidDateFormatException, SecurityException, NoDeleteRightsException;

    /**
     * Добавляет дополнительную информацию к тренировке.
     *
     * @param user            пользователь, для которого добавляется дополнительная информация
     * @param training        тренировка, к которой добавляется информация
     * @param additionalName  имя дополнительной информации
     * @param additionalValue значение дополнительной информации
     * @throws RepositoryException     если возникла ошибка доступа к хранилищу
     * @throws SecurityException       если возникла ошибка безопасности
     * @throws NoWriteRightsException  если у пользователя нет прав на запись
     */
    void addTrainingAdditional(User user, Training training, String additionalName, String additionalValue) throws RepositoryException, SecurityException, NoWriteRightsException;

    /**
     * Удаляет дополнительную информацию из тренировки.
     *
     * @param user           пользователь, для которого удаляется дополнительная информация
     * @param training       тренировка, из которой удаляется информация
     * @param additionalName имя дополнительной информации
     * @throws RepositoryException   если возникла ошибка доступа к хранилищу
     * @throws SecurityException     если возникла ошибка безопасности
     * @throws NoEditRightsException если у пользователя нет прав на редактирование
     */
    void removeTrainingAdditional(User user, Training training, String additionalName) throws RepositoryException, SecurityException, NoEditRightsException;

    /**
     * Изменяет название тренировки.
     *
     * @param user     пользователь, для которого изменяется название тренировки
     * @param training тренировка, у которой изменяется название
     * @param newName  новое название тренировки
     * @throws RepositoryException   если возникла ошибка доступа к хранилищу
     * @throws SecurityException     если возникла ошибка безопасности
     * @throws NoEditRightsException если у пользователя нет прав на редактирование
     */
    void changeNameTraining(User user, Training training, String newName) throws RepositoryException, SecurityException, NoEditRightsException;

    /**
     * Изменяет дату тренировки.
     *
     * @param user     пользователь, для которого изменяется дата тренировки
     * @param training тренировка, у которой изменяется дата
     * @param newDate  новая дата тренировки
     * @throws RepositoryException         если возникла ошибка доступа к хранилищу
     * @throws InvalidDateFormatException если указана некорректная дата тренировки
     * @throws SecurityException           если возникла ошибка безопасности
     * @throws NoEditRightsException       если у пользователя нет прав на редактирование
     */
    void changeDateTraining(User user, Training training, String newDate) throws RepositoryException, InvalidDateFormatException, NoEditRightsException;

    /**
     * Изменяет длительность тренировки.
     *
     * @param user      пользователь, для которого изменяется длительность тренировки
     * @param training  тренировка, у которой изменяется длительность
     * @param newDuration новая длительность тренировки
     * @throws RepositoryException   если возникла ошибка доступа к хранилищу
     * @throws NoEditRightsException если у пользователя нет прав на редактирование
     */
    void changeDurationTraining(User user, Training training, int newDuration) throws RepositoryException, NoEditRightsException;

    /**
     * Изменяет количество сожженных калорий на тренировке.
     *
     * @param user       пользователь, для которого изменяется количество сожженных калорий
     * @param training   тренировка, у которой изменяется количество сожженных калорий
     * @param newCalories новое количество сожженных калорий
     * @throws RepositoryException   если возникла ошибка доступа к хранилищу
     * @throws NoEditRightsException если у пользователя нет прав на редактирование
     */
    void changeCaloriesTraining(User user, Training training, int newCalories) throws RepositoryException, NoEditRightsException;
}

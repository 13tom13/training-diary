package in.repository.training;

import exceptions.RepositoryException;
import model.Training;
import model.User;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Интерфейс для работы с хранилищем тренировок.
 */
public interface TrainingRepository {

    /**
     * Получить все тренировки для указанного пользователя, сгруппированные по дате.
     *
     * @param user электронная почта пользователя
     * @return отображение даты на множество тренировок
     */
    TreeMap<String, TreeSet<Training>> getAllTrainingsByUserID(User user);

    /**
     * Получить тренировки для указанного пользователя на указанную дату.
     *
     * @param user         электронная почта пользователя
     * @param trainingDate дата тренировки
     * @return множество тренировок на указанную дату
     * @throws RepositoryException если тренировка не найдена или возникла ошибка при доступе к хранилищу
     */
    TreeSet<Training> getTrainingsByUserIDAndData(User user, String trainingDate) throws RepositoryException;

    /**
     * Получить тренировку по электронной почте пользователя, дате и названию.
     *
     * @param user         электронная почта пользователя
     * @param trainingDate дата тренировки
     * @param trainingName название тренировки
     * @return тренировка
     * @throws RepositoryException если тренировка не найдена или возникла ошибка при доступе к хранилищу
     */
    Training getTrainingByUserIDlAndDataAndName(User user, String trainingDate, String trainingName) throws RepositoryException;

    /**
     * Сохранить новую тренировку для указанного пользователя.
     *
     * @param user  пользователь
     * @param newTraining новая тренировка
     * @throws RepositoryException если тренировка уже существует или возникла ошибка при доступе к хранилищу
     */
    void saveTraining(User user, Training newTraining) throws RepositoryException;

    /**
     * Удалить указанную тренировку для указанного пользователя.
     *
     * @param user     электронная почта пользователя
     * @param training тренировка для удаления
     * @return
     * @throws RepositoryException если тренировка для удаления не найдена или возникла ошибка при доступе к хранилищу
     */
    boolean deleteTraining(User user, Training training) throws RepositoryException;

    /**
     * Обновить существующую тренировку для указанного пользователя.
     *
     * @param user        электронная почта пользователя
     * @param oldTraining старая версия тренировки
     * @param newTraining новая версия тренировки
     * @throws RepositoryException если тренировка для обновления не найдена или возникла ошибка при доступе к хранилищу
     */
    void updateTraining(User user, Training oldTraining, Training newTraining) throws RepositoryException;
}

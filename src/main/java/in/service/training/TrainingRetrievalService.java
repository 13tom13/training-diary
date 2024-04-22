package in.service.training;

import exceptions.RepositoryException;
import model.Training;
import model.User;

import java.util.Date;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Интерфейс для извлечения данных о тренировках.
 */
public interface TrainingRetrievalService {

    /**
     * Получает все тренировки для указанного пользователя.
     *
     * @param user пользователь, для которого запрашиваются тренировки
     * @return отображение даты на множество тренировок
     * @throws SecurityException если возникла ошибка безопасности
     */
    TreeMap<Date, TreeSet<Training>> getAllTrainings(User user) throws SecurityException;

    /**
     * Получает тренировки для указанного пользователя на указанную дату.
     *
     * @param user пользователь, для которого запрашиваются тренировки
     * @param data дата тренировки
     * @return множество тренировок на указанную дату
     * @throws RepositoryException если возникла ошибка доступа к хранилищу
     * @throws SecurityException   если возникла ошибка безопасности
     */
    TreeSet<Training> getTrainingsByUserIDAndData(User user, Date data) throws RepositoryException, SecurityException;

    /**
     * Получает тренировку по электронной почте пользователя, дате и названию.
     *
     * @param user          пользователь, для которого запрашивается тренировка
     * @param trainingData  дата тренировки
     * @param trainingName  название тренировки
     * @return тренировка
     * @throws RepositoryException если возникла ошибка доступа к хранилищу
     * @throws SecurityException   если возникла ошибка безопасности
     */
    Training getTrainingByUserIDAndDataAndName(User user, Date trainingData, String trainingName) throws RepositoryException, SecurityException;
}

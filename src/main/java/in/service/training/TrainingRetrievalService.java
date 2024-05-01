package in.service.training;

import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import exceptions.RepositoryException;

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
     * @param userDTO пользователь, для которого запрашиваются тренировки
     * @return отображение даты на множество тренировок
     * @throws SecurityException если возникла ошибка безопасности
     */
    TreeMap<Date, TreeSet<TrainingDTO>> getAllTrainings(UserDTO userDTO) throws SecurityException;

    /**
     * Получает тренировки для указанного пользователя на указанную дату.
     *
     * @param userDTO пользователь, для которого запрашиваются тренировки
     * @param data    дата тренировки
     * @return множество тренировок на указанную дату
     * @throws RepositoryException если возникла ошибка доступа к хранилищу
     * @throws SecurityException   если возникла ошибка безопасности
     */
    TreeSet<TrainingDTO> getTrainingsByUserEmailAndData(UserDTO userDTO, Date data) throws RepositoryException, SecurityException;

    /**
     * Получает тренировку по электронной почте пользователя, дате и названию.
     *
     * @param userDTO      пользователь, для которого запрашивается тренировка
     * @param trainingData дата тренировки
     * @param trainingName название тренировки
     * @return тренировка
     * @throws RepositoryException если возникла ошибка доступа к хранилищу
     * @throws SecurityException   если возникла ошибка безопасности
     */
    TrainingDTO getTrainingByUserEmailAndDataAndName(UserDTO userDTO, Date trainingData, String trainingName) throws RepositoryException, SecurityException;
}

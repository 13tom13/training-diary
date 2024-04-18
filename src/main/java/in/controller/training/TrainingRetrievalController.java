package in.controller.training;

import model.Training;
import model.User;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Интерфейс, определяющий методы для получения информации о тренировках.
 */
public interface TrainingRetrievalController {

    /**
     * Получает все тренировки пользователя.
     *
     * @param user пользователь, чьи тренировки нужно получить
     * @return структура данных, содержащая все тренировки пользователя
     */
    TreeMap<String, TreeSet<Training>> getAllTrainings(User user);

    /**
     * Получает тренировки пользователя по указанной дате.
     *
     * @param user         пользователь, чьи тренировки нужно получить
     * @param trainingDate дата тренировки, для которой нужно получить список тренировок
     * @return список тренировок пользователя по указанной дате
     */
    TreeSet<Training> getTrainingsByUserEmailAndData(User user, String trainingDate);

    /**
     * Получает тренировку пользователя по указанной дате и имени.
     *
     * @param user         пользователь, чьи тренировки нужно получить
     * @param trainingDate дата тренировки, для которой нужно получить тренировку
     * @param trainingName имя тренировки, которую нужно получить
     * @return тренировка пользователя по указанной дате и имени
     */
    Training getTrainingByUserEmailAndDataAndName(User user, String trainingDate, String trainingName);
}

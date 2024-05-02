package in.controller.training;

import entities.dto.TrainingDTO;
import entities.dto.UserDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Интерфейс, определяющий методы для получения информации о тренировках.
 */
public interface TrainingRetrievalController {

    /**
     * Получает все тренировки пользователя.
     *
     * @param userDTO пользователь, чьи тренировки нужно получить
     * @return структура данных, содержащая все тренировки пользователя
     */
    TreeMap<LocalDate, TreeSet<TrainingDTO>> getAllTrainings(UserDTO userDTO);

    /**
     * Получает тренировки пользователя по указанной дате.
     *
     * @param userDTO      пользователь, чьи тренировки нужно получить
     * @param trainingDate дата тренировки, для которой нужно получить список тренировок
     * @return список тренировок пользователя по указанной дате
     */
    TreeSet<TrainingDTO> getTrainingsByUserEmailAndData(UserDTO userDTO, LocalDate trainingDate);

    /**
     * Получает тренировку пользователя по указанной дате и имени.
     *
     * @param userDTO      пользователь, чьи тренировки нужно получить
     * @param trainingDate дата тренировки, для которой нужно получить тренировку
     * @param trainingName имя тренировки, которую нужно получить
     * @return тренировка пользователя по указанной дате и имени
     */
    TrainingDTO getTrainingByUserEmailAndDateAndName(UserDTO userDTO, LocalDate trainingDate, String trainingName);
}

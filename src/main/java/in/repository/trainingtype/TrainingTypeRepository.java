package in.repository.trainingtype;

import java.util.List;

/**
 * Интерфейс для работы с хранилищем типов тренировок.
 */
public interface TrainingTypeRepository {

    /**
     * Получить список типов тренировок для указанного пользователя.
     *
     * @param userEmail электронная почта пользователя
     * @return список типов тренировок
     */
    List<String> getTrainingTypes(String userEmail);

    /**
     * Сохранить новый тип тренировки для указанного пользователя.
     *
     * @param userEmail     электронная почта пользователя
     * @param trainingType  новый тип тренировки
     */
    void saveTrainingType(String userEmail, String trainingType);
}

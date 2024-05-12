package in.repository.trainingtype;

import java.util.List;

/**
 * Интерфейс для работы с хранилищем типов тренировок.
 */
public interface TrainingTypeRepository {

    /**
     * Получить список типов тренировок для указанного пользователя.
     *
     * @param id пользователь
     * @return список типов тренировок
     */
    List<String> getTrainingTypes(long id);

    /**
     * Сохранить новый тип тренировки для указанного пользователя.
     *
     * @param id           пользователь
     * @param trainingType новый тип тренировки
     */
    void saveTrainingType(long id, String trainingType);
}

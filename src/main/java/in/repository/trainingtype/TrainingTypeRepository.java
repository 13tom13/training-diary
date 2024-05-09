package in.repository.trainingtype;

import entity.model.User;

import java.util.List;

/**
 * Интерфейс для работы с хранилищем типов тренировок.
 */
public interface TrainingTypeRepository {

    /**
     * Получить список типов тренировок для указанного пользователя.
     *
     * @param user пользователь
     * @return список типов тренировок
     */
    List<String> getTrainingTypes(User user);

    /**
     * Сохранить новый тип тренировки для указанного пользователя.
     *
     * @param user пользователь
     * @param trainingType  новый тип тренировки
     */
    void saveTrainingType(User user, String trainingType);
}

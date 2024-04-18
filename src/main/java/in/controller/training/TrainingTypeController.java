package in.controller.training;

import model.User;

import java.util.List;

/**
 * Интерфейс, определяющий методы для работы с типами тренировок.
 */
public interface TrainingTypeController {

    /**
     * Получает список типов тренировок для указанного пользователя.
     *
     * @param user пользователь, для которого нужно получить список типов тренировок
     * @return список типов тренировок пользователя
     */
    List<String> getTrainingTypes(User user);

    /**
     * Сохраняет новый тип тренировки для указанного пользователя.
     *
     * @param user               пользователь, для которого нужно сохранить новый тип тренировки
     * @param customTrainingType новый тип тренировки для сохранения
     */
    void saveTrainingType(User user, String customTrainingType);
}

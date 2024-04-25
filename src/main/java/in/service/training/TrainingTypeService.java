package in.service.training;

import model.User;

import java.util.List;

/**
 * Интерфейс для работы с типами тренировок.
 */
public interface TrainingTypeService {

    /**
     * Получает список типов тренировок для указанного пользователя.
     *
     * @param user  пользователь, для которого получаются типы тренировок
     * @return список типов тренировок
     */
    List<String> getTrainingTypes(User user);

    /**
     * Сохраняет пользовательский тип тренировки.
     *
     * @param user              пользователь, для которого сохраняется тип тренировки
     * @param customTrainingType пользовательский тип тренировки для сохранения
     */
    void saveTrainingType(User user, String customTrainingType);
}

package in.service.training;


import java.util.List;

/**
 * Интерфейс для работы с типами тренировок.
 */
public interface TrainingTypeService {

    /**
     * Получает список типов тренировок для указанного пользователя.
     *
     * @param id пользователь, для которого получаются типы тренировок
     * @return список типов тренировок
     */
    List<String> getTrainingTypes(long id);

    /**
     * Сохраняет пользовательский тип тренировки.
     *
     * @param id                 пользователь, для которого сохраняется тип тренировки
     * @param customTrainingType пользовательский тип тренировки для сохранения
     */
    void saveTrainingType(long id, String customTrainingType);
}

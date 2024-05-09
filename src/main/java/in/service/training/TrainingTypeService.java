package in.service.training;

import entity.dto.UserDTO;


import java.util.List;

/**
 * Интерфейс для работы с типами тренировок.
 */
public interface TrainingTypeService {

    /**
     * Получает список типов тренировок для указанного пользователя.
     *
     * @param userDTO  пользователь, для которого получаются типы тренировок
     * @return список типов тренировок
     */
    List<String> getTrainingTypes(UserDTO userDTO);

    /**
     * Сохраняет пользовательский тип тренировки.
     *
     * @param userDTO              пользователь, для которого сохраняется тип тренировки
     * @param customTrainingType пользовательский тип тренировки для сохранения
     */
    void saveTrainingType(UserDTO userDTO, String customTrainingType);
}

package in.controller.training;



import entities.dto.UserDTO;

import java.io.IOException;
import java.util.List;

/**
 * Интерфейс, определяющий методы для работы с типами тренировок.
 */
public interface TrainingTypeController {

    /**
     * Получает список типов тренировок для указанного пользователя.
     *
     * @param userDTO пользователь, для которого нужно получить список типов тренировок
     * @return список типов тренировок пользователя
     */
    List<String> getTrainingTypes(UserDTO userDTO) throws IOException;

    /**
     * Сохраняет новый тип тренировки для указанного пользователя.
     *
     * @param userDTO               пользователь, для которого нужно сохранить новый тип тренировки
     * @param customTrainingType новый тип тренировки для сохранения
     */
    void saveTrainingType(UserDTO userDTO, String customTrainingType) throws IOException;
}

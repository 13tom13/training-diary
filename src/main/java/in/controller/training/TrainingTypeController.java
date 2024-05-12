package in.controller.training;



import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * Интерфейс, определяющий методы для работы с типами тренировок.
 */
public interface TrainingTypeController {

    /**
     * Получает список типов тренировок для указанного пользователя.
     *
     * @param id пользователь, для которого нужно получить список типов тренировок
     * @return список типов тренировок пользователя
     */
    ResponseEntity<?> getTrainingTypes(long id) throws IOException;

    /**
     * Сохраняет новый тип тренировки для указанного пользователя.
     *
     * @param id                 пользователь, для которого нужно сохранить новый тип тренировки
     * @param customTrainingType новый тип тренировки для сохранения
     * @return
     */
    ResponseEntity<Void> saveTrainingType(long id, String customTrainingType) throws IOException;
}

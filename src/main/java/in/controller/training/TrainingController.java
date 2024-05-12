package in.controller.training;

import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import out.messenger.TrainingRequest;

/**
 * Интерфейс, объединяющий функциональность для управления тренировками.
 */
public interface TrainingController extends TrainingRetrievalController,
        TrainingModificationController, TrainingTypeController {
}

package out.menu.training;


import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import in.controller.training.TrainingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.TreeMap;
import java.util.TreeSet;

import static utils.Utils.printAllTraining;

/**
 * Представляет класс для просмотра тренировок пользователя.
 */
@Component
public class ViewTraining {

    private final TrainingController trainingController;

    /**
     * Создает экземпляр ViewTraining с заданным контроллером тренировок.
     */
    @Autowired
    public ViewTraining(TrainingController trainingController) {
        this.trainingController = trainingController;
    }

    /**
     * Отображает все тренировки пользователя.
     *
     * @param userDTO Пользователь, чьи тренировки необходимо отобразить.
     */
    public void viewAllTraining(UserDTO userDTO) {
        TreeMap<LocalDate, TreeSet<TrainingDTO>> allTraining = trainingController.getAllTrainings(userDTO);
        printAllTraining(allTraining);
    }
}

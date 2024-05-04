package out.menu.training;


import config.initializer.ControllerFactory;
import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import in.controller.training.TrainingController;

import java.time.LocalDate;
import java.util.TreeMap;
import java.util.TreeSet;

import static utils.Utils.printAllTraining;

/**
 * Представляет класс для просмотра тренировок пользователя.
 */
public class ViewTraining {

    private final TrainingController trainingController;

    /**
     * Создает экземпляр ViewTraining с заданным контроллером тренировок.
     */
    public ViewTraining() {
        this.trainingController = ControllerFactory.getInstance().getTrainingController();
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

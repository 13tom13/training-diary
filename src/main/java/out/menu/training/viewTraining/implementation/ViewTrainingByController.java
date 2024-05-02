package out.menu.training.viewTraining.implementation;


import config.initializer.in.ControllerFactory;
import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import in.controller.training.TrainingController;
import out.menu.training.viewTraining.ViewTraining;

import java.util.Date;
import java.util.TreeMap;
import java.util.TreeSet;

import static utils.Utils.printAllTraining;

/**
 * Представляет класс для просмотра тренировок пользователя.
 */
public class ViewTrainingByController implements ViewTraining {

    private final TrainingController trainingController;

    /**
     * Создает экземпляр ViewTrainingByController с заданным контроллером тренировок.
     */
    public ViewTrainingByController() {
        this.trainingController = ControllerFactory.getInstance().getTrainingController();
    }

    /**
     * Отображает все тренировки пользователя.
     *
     * @param userDTO Пользователь, чьи тренировки необходимо отобразить.
     */
    public void viewAllTraining(UserDTO userDTO) {
        TreeMap<Date, TreeSet<TrainingDTO>> allTraining = trainingController.getAllTrainings(userDTO);
        printAllTraining(allTraining);
    }
}

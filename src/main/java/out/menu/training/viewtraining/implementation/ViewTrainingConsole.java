package out.menu.training.viewtraining.implementation;


import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import in.controller.training.TrainingController;
import out.menu.training.viewtraining.ViewTraining;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import static utils.Utils.getFormattedDate;

/**
 * Представляет класс для просмотра тренировок пользователя.
 */
public class ViewTrainingConsole implements ViewTraining {

    private final TrainingController trainingController;

    /**
     * Создает экземпляр ViewTrainingConsole с заданным контроллером тренировок.
     *
     * @param trainingController Контроллер тренировок.
     */
    public ViewTrainingConsole(TrainingController trainingController) {
        this.trainingController = trainingController;
    }

    /**
     * Отображает все тренировки пользователя.
     *
     * @param userDTO Пользователь, чьи тренировки необходимо отобразить.
     */
    public void viewAllTraining(UserDTO userDTO) {
        TreeMap<Date, TreeSet<TrainingDTO>> allTraining = trainingController.getAllTrainings(userDTO);
        if (allTraining.isEmpty()) {
            System.out.println("Список тренировок пуст");
            return;
        }

        for (Map.Entry<Date, TreeSet<TrainingDTO>> entry : allTraining.entrySet()) {

            String currentDate = getFormattedDate(entry.getKey());
            TreeSet<TrainingDTO> trainingsOnDate = entry.getValue();

            System.out.println("\n" + "=====" + currentDate + "=====");

            for (TrainingDTO training : trainingsOnDate) {
                System.out.println(training);
                System.out.println("--------------------------------------------------");
            }
        }
    }
}

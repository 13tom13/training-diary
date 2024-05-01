package out.menu.training;


import entities.dto.UserDTO;
import in.controller.training.TrainingController;
import entities.model.Training;
import entities.model.User;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import static utils.Utils.getFormattedDate;

/**
 * Представляет класс для просмотра тренировок пользователя.
 */
public class ViewTraining {

    private final TrainingController trainingController;

    /**
     * Создает экземпляр ViewTraining с заданным контроллером тренировок.
     *
     * @param trainingController Контроллер тренировок.
     */
    public ViewTraining(TrainingController trainingController) {
        this.trainingController = trainingController;
    }

    /**
     * Отображает все тренировки пользователя.
     *
     * @param userDTO Пользователь, чьи тренировки необходимо отобразить.
     */
    public void viewAllTraining(UserDTO userDTO) {
        TreeMap<Date, TreeSet<Training>> allTraining = trainingController.getAllTrainings(userDTO);
        if (allTraining.isEmpty()) {
            System.out.println("Список тренировок пуст");
            return;
        }

        for (Map.Entry<Date, TreeSet<Training>> entry : allTraining.entrySet()) {

            String currentDate = getFormattedDate(entry.getKey());
            TreeSet<Training> trainingsOnDate = entry.getValue();

            System.out.println("\n" + "=====" + currentDate + "=====");

            for (Training training : trainingsOnDate) {
                System.out.println(training);
                System.out.println("--------------------------------------------------");
            }
        }
    }
}

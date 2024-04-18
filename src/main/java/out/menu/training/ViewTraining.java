package out.menu.training;


import in.controller.training.TrainingController;
import model.Training;
import model.User;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

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
     * @param user Пользователь, чьи тренировки необходимо отобразить.
     */
    public void viewAllTraining(User user) {
        TreeMap<String, TreeSet<Training>> allTraining = trainingController.getAllTrainings(user);
        if (allTraining.isEmpty()) {
            System.out.println("Список тренировок пуст");
            return;
        }

        for (Map.Entry<String, TreeSet<Training>> entry : allTraining.entrySet()) {

            String currentDate = entry.getKey();
            TreeSet<Training> trainingsOnDate = entry.getValue();

            System.out.println("\n" + "=====" + currentDate + "=====");

            for (Training training : trainingsOnDate) {
                System.out.println(training);
                System.out.println("--------------------------------------------------");
            }
        }
    }
}

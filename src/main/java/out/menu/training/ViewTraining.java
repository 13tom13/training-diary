package out.menu.training;


import in.controller.TrainingController;
import in.model.Training;
import in.model.User;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class ViewTraining {

    private final TrainingController trainingController;


    public ViewTraining(TrainingController trainingController) {
        this.trainingController = trainingController;
    }

    public void viewAllTraining(User user) {
        TreeMap<String, TreeSet<Training>> allTraining = trainingController.getAllTrainings(user);
        if (allTraining.isEmpty()) {
            System.out.println("список тренировок пуст");
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

package out.menu.authorization.account.training;


import in.controller.training.TrainingController;
import in.model.Training;
import in.model.User;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class ViewTraining {

    private final TrainingController trainingController;

    private final User user;


    public ViewTraining(TrainingController trainingController, User user) {
        this.trainingController = trainingController;
        this.user = user;
    }

    public void viewAllTraining() {
        TreeMap<String, TreeSet<Training>> allTraining = trainingController.getAllTrainings(user.getEmail());
        for (Map.Entry<String, TreeSet<Training>> entry : allTraining.entrySet()) {
            String currentDate = entry.getKey();
            TreeSet<Training> trainingsOnDate = entry.getValue();

            SimpleDateFormat dateFormat = new SimpleDateFormat(Training.DATE_FORMAT);
            String formattedDate = dateFormat.format(currentDate);

            System.out.println("\n" + "=====" + formattedDate + "=====");

            for (Training training : trainingsOnDate) {
                System.out.println(training);
                System.out.println("--------------------------------------------------");
            }
        }
    }


}

package out.menu.authorization.account.training;


import in.controller.training.TrainingController;
import in.model.Training;
import in.model.User;

import java.util.TreeSet;

public class ViewTraining {

    private final TrainingController trainingController;

    private final User user;


    public ViewTraining(TrainingController trainingController, User user) {
        this.trainingController = trainingController;
        this.user = user;
    }

    public void viewAllTraining() {
        TreeSet<Training> allTraining = trainingController.getAllTrainings(user.getEmail());
        String previousDate = "";
        for (Training training : allTraining) {
            String currentDate = String.valueOf(training.getDate());
            if (!currentDate.equals(previousDate)) {
                System.out.println("\n" + currentDate + ":");
                previousDate = currentDate;
            }
            System.out.println(training);
        }
    }
}

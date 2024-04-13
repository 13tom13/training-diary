package out;

import in.controller.AuthorizationController;
import in.controller.training.TrainingController;
import in.controller.UserController;
import in.controller.training.TrainingStatisticsController;
import in.controller.training.implementation.TrainingStatisticsControllerImpl;
import out.menu.ViewMenu;

public class TrainingDiary {
    private final ViewMenu viewMenu;

    public TrainingDiary(AuthorizationController authorizationController,
                         UserController userController,
                         TrainingController trainingController,
                         TrainingStatisticsController trainingStatisticsController) {
        this.viewMenu = new ViewMenu(authorizationController, userController,
                trainingController, trainingStatisticsController);
    }

    public void start() {
        viewMenu.processMainMenuChoice();
    }
}
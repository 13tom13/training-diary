package out;

import in.controller.AdminController;
import in.controller.AuthorizationController;
import in.controller.UserController;
import in.controller.training.TrainingController;
import in.controller.training.TrainingStatisticsController;
import out.menu.ViewMenu;

public class TrainingDiary {
    private final ViewMenu viewMenu;


    public TrainingDiary(AuthorizationController authorizationController,
                         AdminController adminController,
                         UserController userController,
                         TrainingController trainingController,
                         TrainingStatisticsController trainingStatisticsController) {
        this.viewMenu = new ViewMenu(authorizationController, adminController, userController,
                trainingController, trainingStatisticsController);
    }

    public void start() {
        viewMenu.processMainMenuChoice();
    }
}
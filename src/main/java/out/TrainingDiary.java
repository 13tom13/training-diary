package out;

import in.controller.*;
import in.controller.AuthorizationController;
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
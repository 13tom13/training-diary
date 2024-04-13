package out;

import in.controller.AuthorizationController;
import in.controller.training.TrainingController;
import in.controller.UserController;
import out.menu.ViewMenu;

public class TrainingDiary {
    private final ViewMenu viewMenu;

    public TrainingDiary(AuthorizationController authorizationController,
                         UserController userController,
                         TrainingController trainingController) {
        this.viewMenu = new ViewMenu(authorizationController, userController, trainingController);
    }

    public void start() {
        viewMenu.processMainMenuChoice();
    }
}
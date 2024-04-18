package out;

import config.ControllerInitializer;
import out.menu.ViewMenu;

public class TrainingDiary {
    private final ViewMenu viewMenu;

    public TrainingDiary() {
        ControllerInitializer controllerInitializer = new ControllerInitializer();
        this.viewMenu = new ViewMenu(controllerInitializer.getAuthorizationController(),
                controllerInitializer.getAdminController(),
                controllerInitializer.getUserController(),
                controllerInitializer.getTrainingController(),
                controllerInitializer.getTrainingStatisticsController());
    }

    public void start() {
        viewMenu.processMainMenuChoice();
    }


}
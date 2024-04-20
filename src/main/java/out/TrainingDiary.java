package out;

import config.initializer.ControllerInitializer;
import out.menu.ViewMenu;

/**
 * Класс TrainingDiary представляет собой приложение для ведения тренировочного дневника.
 */
public class TrainingDiary {
    private final ViewMenu viewMenu;

    /**
     * Конструктор класса TrainingDiary.
     * Инициализирует объект ViewMenu с помощью контроллеров из ControllerInitializer.
     */
    public TrainingDiary() {
        ControllerInitializer controllerInitializer = new ControllerInitializer();
        this.viewMenu = new ViewMenu(controllerInitializer.getAuthorizationController(),
                controllerInitializer.getAdminController(),
                controllerInitializer.getUserController(),
                controllerInitializer.getTrainingController(),
                controllerInitializer.getTrainingStatisticsController());
    }

    /**
     * Метод start запускает приложение и отображает главное меню.
     */
    public void start() {
        viewMenu.processMainMenuChoice();
    }
}

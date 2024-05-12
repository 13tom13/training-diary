package out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import out.menu.ViewMenu;

/**
 * Класс TrainingDiary представляет собой приложение для ведения тренировочного дневника.
 */
@Component
public class TrainingDiary {
    private final ViewMenu viewMenu;

    /**
     * Конструктор класса TrainingDiary.
     * Инициализирует объект ViewMenu с помощью контроллеров из ControllerInitializer.
     */
    @Autowired
    public TrainingDiary(ViewMenu viewMenu) {
        this.viewMenu = viewMenu;
        this.logo();
    }

    /**
     * Метод start запускает приложение и отображает главное меню.
     */
    public void start() {
        viewMenu.processMainMenuChoice();
    }

    private void logo() {
        System.out.println("""
                  1111111    333333333333333   TTTTTTTTTTTTTTTTTTTTTTT     OOOOOOOOO     MMMMMMMM               MMMMMMMM  1111111    333333333333333   
                 1::::::1   3:::::::::::::::33 T:::::::::::::::::::::T   OO:::::::::OO   M:::::::M             M:::::::M 1::::::1   3:::::::::::::::33 
                1:::::::1   3::::::33333::::::3T:::::::::::::::::::::T OO:::::::::::::OO M::::::::M           M::::::::M1:::::::1   3::::::33333::::::3
                111:::::1   3333333     3:::::3T:::::TT:::::::TT:::::TO:::::::OOO:::::::OM:::::::::M         M:::::::::M111:::::1   3333333     3:::::3
                   1::::1               3:::::3TTTTTT  T:::::T  TTTTTTO::::::O   O::::::OM::::::::::M       M::::::::::M   1::::1               3:::::3
                   1::::1               3:::::3        T:::::T        O:::::O     O:::::OM:::::::::::M     M:::::::::::M   1::::1               3:::::3
                   1::::1       33333333:::::3         T:::::T        O:::::O     O:::::OM:::::::M::::M   M::::M:::::::M   1::::1       33333333:::::3 
                   1::::l       3:::::::::::3          T:::::T        O:::::O     O:::::OM::::::M M::::M M::::M M::::::M   1::::l       3:::::::::::3  
                   1::::l       33333333:::::3         T:::::T        O:::::O     O:::::OM::::::M  M::::M::::M  M::::::M   1::::l       33333333:::::3 
                   1::::l               3:::::3        T:::::T        O:::::O     O:::::OM::::::M   M:::::::M   M::::::M   1::::l               3:::::3
                   1::::l               3:::::3        T:::::T        O:::::O     O:::::OM::::::M    M:::::M    M::::::M   1::::l               3:::::3
                   1::::l               3:::::3        T:::::T        O::::::O   O::::::OM::::::M     MMMMM     M::::::M   1::::l               3:::::3
                111::::::1113333333     3:::::3      TT:::::::TT      O:::::::OOO:::::::OM::::::M               M::::::M111::::::1113333333     3:::::3
                1::::::::::13::::::33333::::::3      T:::::::::T       OO:::::::::::::OO M::::::M               M::::::M1::::::::::13::::::33333::::::3
                1::::::::::13:::::::::::::::33       T:::::::::T         OO:::::::::OO   M::::::M               M::::::M1::::::::::13:::::::::::::::33 
                111111111111 333333333333333         TTTTTTTTTTT           OOOOOOOOO     MMMMMMMM               MMMMMMMM111111111111 333333333333333   
                """);
    }
}

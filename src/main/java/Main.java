import config.database.DatabaseConnection;
import in.controller.users.UserController;
import in.repository.user.implementation.UserRepositorySpringJPA;
import in.service.users.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import out.TrainingDiary;

/**
 * Класс Main является точкой входа в программу.
 */
public class Main {

    /**
     * Метод main - точка входа в программу.
     * @param args Аргументы командной строки.
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("in", "out","database", "config");
        context.refresh();
        context.getBean(DatabaseConnection.class);
        context.getBean(UserController.class);
        context.getBean(UserService.class);
        context.getBean(UserRepositorySpringJPA.class);

        // Создание объекта TrainingDiary
        TrainingDiary trainingDiary = context.getBean(TrainingDiary.class);

        // Вызов метода start() объекта TrainingDiary
        trainingDiary.start();
    }

}

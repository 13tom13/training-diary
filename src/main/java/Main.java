import config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import out.TrainingDiary;

/**
 * Класс Main является точкой входа в программу.
 */
public class Main {

    /**
     * Метод main - точка входа в программу.
     *
     * @param args Аргументы командной строки.
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        context.start();
        // Создание объекта TrainingDiary
        TrainingDiary trainingDiary = context.getBean(TrainingDiary.class);

        // Вызов метода start() объекта TrainingDiary
        trainingDiary.start();
    }

}

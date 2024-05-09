import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
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
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.scan("config", "util", "in", "out");

        // Создание объекта TrainingDiary
        TrainingDiary trainingDiary = context.getBean(TrainingDiary.class);

        // Вызов метода start() объекта TrainingDiary
        trainingDiary.start();
    }

}

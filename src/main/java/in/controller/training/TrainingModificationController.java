package in.controller.training;

import entities.model.Training;
import entities.model.User;

import java.util.Date;

/**
 * Интерфейс, определяющий методы для модификации тренировок.
 */
public interface TrainingModificationController {

    /**
     * Сохраняет новую тренировку для пользователя.
     *
     * @param user     пользователь, для которого сохраняется тренировка
     * @param training тренировка, которую нужно сохранить
     * @return true, если тренировка успешно сохранена, в противном случае false
     */
    boolean saveTraining(User user, Training training);

    /**
     * Удаляет тренировку пользователя по указанной дате и имени.
     *
     * @param user пользователь, для которого удаляется тренировка
     * @param date дата тренировки
     * @param name имя тренировки
     * @return true, если тренировка успешно удалена, в противном случае false
     */
    boolean deleteTraining(User user, Date date, String name);

    /**
     * Добавляет дополнительную информацию к тренировке.
     *
     * @param user            пользователь, для которого добавляется информация
     * @param training        тренировка, к которой добавляется информация
     * @param additionalName  название дополнительной информации
     * @param additionalValue значение дополнительной информации
     * @return измененная тренировка
     */
    Training addTrainingAdditional(User user, Training training, String additionalName, String additionalValue);

    /**
     * Удаляет дополнительную информацию из тренировки.
     *
     * @param user           пользователь, для которого удаляется информация
     * @param training       тренировка, из которой удаляется информация
     * @param additionalName название дополнительной информации
     * @return измененная тренировка
     */
    Training removeTrainingAdditional(User user, Training training, String additionalName);

    /**
     * Изменяет имя тренировки.
     *
     * @param user     пользователь, для которого изменяется имя тренировки
     * @param training тренировка, у которой изменяется имя
     * @param newName  новое имя тренировки
     * @return измененная тренировка
     */
    Training changeNameTraining(User user, Training training, String newName);

    /**
     * Изменяет дату тренировки.
     *
     * @param user     пользователь, для которого изменяется дата тренировки
     * @param training тренировка, у которой изменяется дата
     * @param newDate  новая дата тренировки
     * @return измененная тренировка
     */
    Training changeDateTraining(User user, Training training, Date newDate);

    /**
     * Изменяет продолжительность тренировки.
     *
     * @param user        пользователь, для которого изменяется продолжительность тренировки
     * @param training    тренировка, у которой изменяется продолжительность
     * @param newDuration новая продолжительность тренировки
     * @return измененная тренировка
     */
    Training changeDurationTraining(User user, Training training, String newDuration);

    /**
     * Изменяет количество сожженных калорий на тренировке.
     *
     * @param user        пользователь, для которого изменяется количество калорий на тренировке
     * @param training    тренировка, у которой изменяется количество калорий
     * @param newCalories новое количество сожженных калорий
     * @return измененная тренировка
     */
    Training changeCaloriesTraining(User user, Training training, String newCalories);
}
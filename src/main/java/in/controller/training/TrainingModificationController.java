package in.controller.training;

import model.Training;
import model.User;

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
     */
    void deleteTraining(User user, String date, String name);

    /**
     * Добавляет дополнительную информацию к тренировке.
     *
     * @param user            пользователь, для которого добавляется информация
     * @param training        тренировка, к которой добавляется информация
     * @param additionalName  название дополнительной информации
     * @param additionalValue значение дополнительной информации
     */
    void addTrainingAdditional(User user, Training training, String additionalName, String additionalValue);

    /**
     * Удаляет дополнительную информацию из тренировки.
     *
     * @param user           пользователь, для которого удаляется информация
     * @param training       тренировка, из которой удаляется информация
     * @param additionalName название дополнительной информации
     */
    void removeTrainingAdditional(User user, Training training, String additionalName);

    /**
     * Изменяет имя тренировки.
     *
     * @param user     пользователь, для которого изменяется имя тренировки
     * @param training тренировка, у которой изменяется имя
     * @param newName  новое имя тренировки
     */
    void changeNameTraining(User user, Training training, String newName);

    /**
     * Изменяет дату тренировки.
     *
     * @param user     пользователь, для которого изменяется дата тренировки
     * @param training тренировка, у которой изменяется дата
     * @param newDate  новая дата тренировки
     */
    void changeDateTraining(User user, Training training, String newDate);

    /**
     * Изменяет продолжительность тренировки.
     *
     * @param user        пользователь, для которого изменяется продолжительность тренировки
     * @param training    тренировка, у которой изменяется продолжительность
     * @param newDuration новая продолжительность тренировки
     */
    void changeDurationTraining(User user, Training training, String newDuration);

    /**
     * Изменяет количество сожженных калорий на тренировке.
     *
     * @param user          пользователь, для которого изменяется количество калорий на тренировке
     * @param training      тренировка, у которой изменяется количество калорий
     * @param newCalories   новое количество сожженных калорий
     */
    void changeCaloriesTraining(User user, Training training, String newCalories);
}

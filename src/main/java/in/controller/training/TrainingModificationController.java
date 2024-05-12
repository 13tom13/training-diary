package in.controller.training;

import entity.dto.TrainingDTO;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoWriteRightsException;
import org.springframework.http.ResponseEntity;
import out.messenger.TrainingRequest;


import java.time.LocalDate;

/**
 * Интерфейс, определяющий методы для модификации тренировок.
 */
public interface TrainingModificationController {

    /**
     * Сохраняет новую тренировку для пользователя.
     *
     * @return true, если тренировка успешно сохранена, в противном случае false
     */
    ResponseEntity<?> saveTraining(TrainingRequest request) throws InvalidDateFormatException, NoWriteRightsException, RepositoryException;

    /**
     * Удаляет тренировку пользователя по указанной дате и имени.
     *
     * @param email пользователь, для которого удаляется тренировка
     * @param date  дата тренировки
     * @param name  имя тренировки
     * @return
     */
    ResponseEntity<?> deleteTraining(String email, String date, String name) throws NoDeleteRightsException, RepositoryException;

    /**
     * Добавляет дополнительную информацию к тренировке.
     *
     * @param additionalName  название дополнительной информации
     * @param additionalValue значение дополнительной информации
     * @return измененная тренировка
     */
    ResponseEntity<?> addTrainingAdditional(String additionalName, String additionalValue, TrainingRequest trainingRequest);

    /**
     * Удаляет дополнительную информацию из тренировки.
     *
     * @param trainingDTO    тренировка, из которой удаляется информация
     * @param email          пользователь, для которого удаляется информация
     * @param additionalName название дополнительной информации
     * @return измененная тренировка
     */
    ResponseEntity<?> removeTrainingAdditional(TrainingDTO trainingDTO, String email, String additionalName);

    /**
     * Изменяет имя тренировки.
     *
     * @param trainingDTO тренировка, у которой изменяется имя
     * @param email       пользователь, для которого изменяется имя тренировки
     * @param newName     новое имя тренировки
     * @return измененная тренировка
     */
    ResponseEntity<?> changeNameTraining(TrainingDTO trainingDTO, String email, String newName) throws RepositoryException;

    /**
     * Изменяет дату тренировки.
     *
     * @param trainingDTO тренировка, у которой изменяется дата
     * @param email       пользователь, для которого изменяется дата тренировки
     * @param newDate     новая дата тренировки
     * @return измененная тренировка
     */
    ResponseEntity<?> changeDateTraining(TrainingDTO trainingDTO, String email, LocalDate newDate) throws RepositoryException;

    /**
     * Изменяет продолжительность тренировки.
     *
     * @param trainingDTO тренировка, у которой изменяется продолжительность
     * @param email       пользователь, для которого изменяется продолжительность тренировки
     * @param newDuration новая продолжительность тренировки
     * @return измененная тренировка
     */
    ResponseEntity<?> changeDurationTraining(TrainingDTO trainingDTO, String email, int newDuration) throws RepositoryException;

    /**
     * Изменяет количество сожженных калорий на тренировке.
     *
     * @param trainingDTO тренировка, у которой изменяется количество калорий
     * @param email       пользователь, для которого изменяется количество калорий на тренировке
     * @param newCalories новое количество сожженных калорий
     * @return измененная тренировка
     */
    ResponseEntity<?> changeCaloriesTraining(TrainingDTO trainingDTO, String email, int newCalories) throws RepositoryException;
}
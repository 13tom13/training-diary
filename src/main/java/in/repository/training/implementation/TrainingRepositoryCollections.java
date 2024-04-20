package in.repository.training.implementation;

import exceptions.RepositoryException;
import model.Training;
import in.repository.training.TrainingRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Реализация интерфейса {@link TrainingRepository} для хранения тренировок.
 * Этот класс обеспечивает методы для сохранения, получения и удаления тренировок пользователя.
 */
public class TrainingRepositoryCollections implements TrainingRepository {

    /**
     * Map для хранения тренировок пользователей.
     * Ключами являются адреса электронной почты пользователей.
     * Значениями являются TreeMap, где ключами являются даты тренировок,
     * а значениями являются сами тренировки пользователя, совершенные в указанные даты.
     */
    private final Map<String, TreeMap<String, TreeSet<Training>>> userTrainingMap;

    /**
     * Создает новый экземпляр класса TrainingRepositoryCollections.
     * Инициализирует внутреннюю структуру данных для хранения тренировок пользователей.
     */
    public TrainingRepositoryCollections() {
        userTrainingMap = new HashMap<>();
    }

    /**
     * Получает все тренировки пользователя по его адресу электронной почты.
     * Если пользователь с указанным адресом не найден, возвращает пустую TreeMap.
     *
     * @param userEmail адрес электронной почты пользователя
     * @return TreeMap, содержащий все тренировки пользователя
     */
    @Override
    public TreeMap<String, TreeSet<Training>> getAllTrainingsByUserEmail(String userEmail) {
        return userTrainingMap.getOrDefault(userEmail, new TreeMap<>());
    }

    /**
     * Получает тренировки пользователя по его адресу электронной почты и дате тренировки.
     * Если пользователь с указанным адресом не найден или тренировка на указанную дату отсутствует,
     * выбрасывает исключение RepositoryException.
     *
     * @param userEmail   адрес электронной почты пользователя
     * @param trainingDate дата тренировки
     * @return множество тренировок пользователя на указанную дату
     * @throws RepositoryException если тренировка не найдена или возникла ошибка при доступе к хранилищу
     */
    @Override
    public TreeSet<Training> getTrainingsByUserEmailAndData(String userEmail, String trainingDate) throws RepositoryException {
        TreeMap<String, TreeSet<Training>> userTrainings = userTrainingMap.get(userEmail);
        if (userTrainings != null) {
            TreeSet<Training> trainingsOnDate = userTrainings.get(trainingDate);
            if (trainingsOnDate != null && !trainingsOnDate.isEmpty()) {
                return trainingsOnDate;
            }
        }
        throw new RepositoryException("Тренировка с датой " + trainingDate + " не найдена для пользователя с email " + userEmail);
    }

    /**
     * Получает тренировку пользователя по его адресу электронной почты, дате и имени тренировки.
     * Если пользователь с указанным адресом не найден, тренировка на указанную дату отсутствует
     * или тренировка с указанным именем не найдена, выбрасывает исключение RepositoryException.
     *
     * @param userEmail    адрес электронной почты пользователя
     * @param trainingDate дата тренировки
     * @param trainingName имя тренировки
     * @return тренировка пользователя на указанную дату и с указанным именем
     * @throws RepositoryException если тренировка не найдена или возникла ошибка при доступе к хранилищу
     */
    @Override
    public Training getTrainingByUserEmailAndDataAndName(String userEmail, String trainingDate, String trainingName) throws RepositoryException {
        TreeMap<String, TreeSet<Training>> userTrainings = userTrainingMap.get(userEmail);
        if (userTrainings != null) {
            TreeSet<Training> trainingsOnDate = userTrainings.get(trainingDate);
            if (trainingsOnDate != null && !trainingsOnDate.isEmpty()) {
                for (Training training : trainingsOnDate) {
                    if (training.getName().equals(trainingName)) {
                        return training;
                    }
                }
                throw new RepositoryException("Тренировка с именем " + trainingName + " не найдена в тренировках с датой " + trainingDate + " для пользователя с email " + userEmail);
            } else {
                throw new RepositoryException("На указанную дату " + trainingDate + " нет тренировок для пользователя с email " + userEmail);
            }
        } else {
            throw new RepositoryException("Пользователь с email " + userEmail + " не найден в тренировках");
        }
    }

    /**
     * Сохраняет новую тренировку пользователя.
     * Если для указанного пользователя уже существует тренировка на указанную дату с тем же именем,
     * выбрасывает исключение RepositoryException.
     *
     * @param userEmail   адрес электронной почты пользователя
     * @param newTraining новая тренировка пользователя
     * @throws RepositoryException если тренировка уже существует или возникла ошибка при доступе к хранилищу
     */
    @Override
    public void saveTraining(String userEmail, Training newTraining) throws RepositoryException {
        TreeMap<String, TreeSet<Training>> userTrainings = userTrainingMap.computeIfAbsent(userEmail, k -> new TreeMap<>());
        TreeSet<Training> trainingsOnDate = userTrainings.computeIfAbsent(newTraining.getDate(), k -> new TreeSet<>());
        if (!trainingsOnDate.add(newTraining)) {
            throw new RepositoryException("Тренировка с именем " + newTraining.getName() + " на дату " + newTraining.getDate() + " уже существует");
        } else {
            userTrainings.put(newTraining.getDate(), trainingsOnDate);
        }
    }

    /**
     * Удаляет указанную тренировку пользователя.
     * Если тренировка для удаления не найдена на указанную дату или для указанного пользователя,
     * выбрасывает исключение RepositoryException.
     *
     * @param userEmail адрес электронной почты пользователя
     * @param training  тренировка для удаления
     * @throws RepositoryException если тренировка для удаления не найдена или возникла ошибка при доступе к хранилищу
     */
    @Override
    public void deleteTraining(String userEmail, Training training) throws RepositoryException {
        TreeMap<String, TreeSet<Training>> userTrainings = userTrainingMap.get(userEmail);
        if (userTrainings != null) {
            TreeSet<Training> trainingsOnDate = userTrainings.get(training.getDate());
            if (trainingsOnDate != null) {
                if (!trainingsOnDate.remove(training)) {
                    throw new RepositoryException("Тренировка для удаления не найдена на указанную дату для пользователя с email " + userEmail);
                }
                if (trainingsOnDate.isEmpty()) {
                    userTrainings.remove(training.getDate());
                }
            } else {
                throw new RepositoryException("На указанную дату " + training.getDate() + " нет тренировок для пользователя с email " + userEmail);
            }
        } else {
            throw new RepositoryException("Пользователь с email " + userEmail + " не найден в тренировках");
        }
    }

    /**
     * Обновляет указанную тренировку пользователя.
     * Если тренировка для обновления не найдена на указанную дату или для указанного пользователя,
     * выбрасывает исключение RepositoryException.
     *
     * @param userEmail   адрес электронной почты пользователя
     * @param oldTraining тренировка для обновления
     * @param newTraining новая версия тренировки
     * @throws RepositoryException если тренировка для обновления не найдена или возникла ошибка при доступе к хранилищу
     */
    @Override
    public void updateTraining(String userEmail, Training oldTraining, Training newTraining) throws RepositoryException {
        TreeMap<String, TreeSet<Training>> userTrainings = userTrainingMap.get(userEmail);
        if (userTrainings != null) {
            TreeSet<Training> trainingsOnDate = userTrainings.get(oldTraining.getDate());
            if (trainingsOnDate != null) {
                if (!trainingsOnDate.remove(oldTraining)) {
                    throw new RepositoryException("Тренировка для обновления не найдена на указанную дату для пользователя с email " + userEmail);
                }
                trainingsOnDate.add(newTraining);
                userTrainings.put(newTraining.getDate(), trainingsOnDate);
            } else {
                throw new RepositoryException("На указанную дату " + oldTraining.getDate() + " нет тренировок для пользователя с email " + userEmail);
            }
        } else {
            throw new RepositoryException("Пользователь с email " + userEmail + " не найден в тренировках");
        }
    }
}

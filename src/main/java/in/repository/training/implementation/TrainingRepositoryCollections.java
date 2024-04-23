package in.repository.training.implementation;

import exceptions.RepositoryException;
import in.repository.training.TrainingRepository;
import model.Training;
import model.User;

import java.util.*;

/**
 * Реализация интерфейса {@link TrainingRepository} для хранения тренировок.
 * Этот класс обеспечивает методы для сохранения, получения и удаления тренировок пользователя.
 */
public class TrainingRepositoryCollections implements TrainingRepository {

    /**
     * Map для хранения тренировок пользователей.
     * Ключами являются идентификаторы пользователей.
     * Значениями являются TreeMap, где ключами являются даты тренировок,
     * а значениями являются сами тренировки пользователя, совершенные в указанные даты.
     */
    private final Map<Long, TreeMap<Date, TreeSet<Training>>> userTrainingMap;

    /**
     * Создает новый экземпляр класса TrainingRepositoryCollections.
     * Инициализирует внутреннюю структуру данных для хранения тренировок пользователей.
     */
    public TrainingRepositoryCollections() {
        userTrainingMap = new HashMap<>();
    }

    /**
     * Получает все тренировки пользователя по его идентификатору.
     * Если пользователь с указанным идентификатором не найден, возвращает пустую TreeMap.
     *
     * @param user пользователь, для которого нужно получить тренировки
     * @return TreeMap, содержащий все тренировки пользователя
     */
    @Override
    public TreeMap<Date, TreeSet<Training>> getAllTrainingsByUserID(User user) {
        return userTrainingMap.getOrDefault(user.getId(), new TreeMap<>());
    }

    /**
     * Получает тренировки пользователя по его идентификатору и дате тренировки.
     * Если пользователь с указанным идентификатором не найден или тренировка на указанную дату отсутствует,
     * выбрасывает исключение RepositoryException.
     *
     * @param user         пользователь, для которого нужно получить тренировки
     * @param trainingDate дата тренировки
     * @return множество тренировок пользователя на указанную дату
     * @throws RepositoryException если тренировка не найдена или возникла ошибка при доступе к хранилищу
     */
    @Override
    public TreeSet<Training> getTrainingsByUserIDAndData(User user, Date trainingDate) throws RepositoryException {
        TreeMap<Date, TreeSet<Training>> userTrainings = userTrainingMap.get(user.getId());
        if (userTrainings != null) {
            TreeSet<Training> trainingsOnDate = userTrainings.get(trainingDate);
            if (trainingsOnDate != null && !trainingsOnDate.isEmpty()) {
                return trainingsOnDate;
            }
        }
        throw new RepositoryException("Тренировка с датой " + trainingDate + " не найдена для пользователя с идентификатором " + user.getId());
    }

    /**
     * Получает тренировку пользователя по его идентификатору, дате и имени тренировки.
     * Если пользователь с указанным идентификатором не найден, тренировка на указанную дату отсутствует
     * или тренировка с указанным именем не найдена, выбрасывает исключение RepositoryException.
     *
     * @param user         пользователь, для которого нужно получить тренировку
     * @param trainingDate дата тренировки
     * @param trainingName имя тренировки
     * @return тренировка пользователя на указанную дату и с указанным именем
     * @throws RepositoryException если тренировка не найдена или возникла ошибка при доступе к хранилищу
     */
    @Override
    public Training getTrainingByUserIDlAndDataAndName(User user, Date trainingDate, String trainingName) throws RepositoryException {
        TreeMap<Date, TreeSet<Training>> userTrainings = userTrainingMap.get(user.getId());
        if (userTrainings != null) {
            TreeSet<Training> trainingsOnDate = userTrainings.get(trainingDate);
            if (trainingsOnDate != null && !trainingsOnDate.isEmpty()) {
                for (Training training : trainingsOnDate) {
                    if (training.getName().equals(trainingName)) {
                        return training;
                    }
                }
                throw new RepositoryException("Тренировка с именем " + trainingName + " не найдена в тренировках с датой " + trainingDate + " для пользователя с идентификатором " + user.getId());
            } else {
                throw new RepositoryException("На указанную дату " + trainingDate + " нет тренировок для пользователя с идентификатором " + user.getId());
            }
        } else {
            throw new RepositoryException("Пользователь с идентификатором " + user.getId() + " не найден в тренировках");
        }
    }

    /**
     * Сохраняет новую тренировку пользователя.
     * Если для указанного пользователя уже существует тренировка на указанную дату с тем же именем,
     * выбрасывает исключение RepositoryException.
     *
     * @param user        пользователь, для которого нужно сохранить тренировку
     * @param newTraining новая тренировка пользователя
     * @throws RepositoryException если тренировка уже существует или возникла ошибка при доступе к хранилищу
     */
    @Override
    public void saveTraining(User user, Training newTraining) throws RepositoryException {
        TreeMap<Date, TreeSet<Training>> userTrainings = userTrainingMap.computeIfAbsent(user.getId(), k -> new TreeMap<>());
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
     * @param user     пользователь, для которого нужно удалить тренировку
     * @param training тренировка для удаления
     * @return {@code true}, если тренировка удалена успешно, в противном случае {@code false}
     * @throws RepositoryException если тренировка для удаления не найдена или возникла ошибка при доступе к хранилищу
     */
    @Override
    public boolean deleteTraining(User user, Training training) throws RepositoryException {
        TreeMap<Date, TreeSet<Training>> userTrainings = userTrainingMap.get(user.getId());
        if (userTrainings != null) {
            TreeSet<Training> trainingsOnDate = userTrainings.get(training.getDate());
            if (trainingsOnDate != null) {
                if (!trainingsOnDate.remove(training)) {
                    throw new RepositoryException("Тренировка для удаления не найдена на указанную дату для пользователя с идентификатором " + user.getId());
                }
                if (trainingsOnDate.isEmpty()) {
                    userTrainings.remove(training.getDate());
                }
                return true;
            } else {
                throw new RepositoryException("На указанную дату " + training.getDate() + " нет тренировок для пользователя с идентификатором " + user.getId());
            }
        } else {
            throw new RepositoryException("Пользователь с идентификатором " + user.getId() + " не найден в тренировках");
        }
    }

    /**
     * Обновляет указанную тренировку пользователя.
     * Если тренировка для обновления не найдена на указанную дату или для указанного пользователя,
     * выбрасывает исключение RepositoryException.
     *
     * @param user        пользователь, для которого нужно обновить тренировку
     * @param oldTraining тренировка для обновления
     * @param newTraining новая версия тренировки
     * @throws RepositoryException если тренировка для обновления не найдена или возникла ошибка при доступе к хранилищу
     */
    @Override
    public void updateTraining(User user, Training oldTraining, Training newTraining) throws RepositoryException {
        TreeMap<Date, TreeSet<Training>> userTrainings = userTrainingMap.get(user.getId());
        if (userTrainings != null) {
            TreeSet<Training> trainingsOnDate = userTrainings.get(oldTraining.getDate());
            if (trainingsOnDate != null) {
                if (!trainingsOnDate.remove(oldTraining)) {
                    throw new RepositoryException("Тренировка для обновления не найдена на указанную дату для пользователя с идентификатором " + user.getId());
                }
                trainingsOnDate.add(newTraining);
                userTrainings.put(newTraining.getDate(), trainingsOnDate);
            } else {
                throw new RepositoryException("На указанную дату " + oldTraining.getDate() + " нет тренировок для пользователя с идентификатором " + user.getId());
            }
        } else {
            throw new RepositoryException("Пользователь с идентификатором " + user.getId() + " не найден в тренировках");
        }
    }
}

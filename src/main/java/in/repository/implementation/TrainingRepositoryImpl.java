package in.repository.implementation;

import in.exception.RepositoryException;
import in.model.Training;
import in.repository.TrainingRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class TrainingRepositoryImpl implements TrainingRepository {

    /**
     * Map для хранения тренировок пользователей.
     * Ключами являются адреса электронной почты пользователей.
     * Значениями являются TreeMap, где ключами являются даты тренировок,
     * а значениями являются сами тренировки пользователя, совершенные в указанные даты.
     */
    private final Map<String, TreeMap<String, TreeSet<Training>>> userTrainingMap = new HashMap<>();

    public TreeMap<String, TreeSet<Training>> getAllTrainingsByUserEmail(String userEmail) {
        return userTrainingMap.getOrDefault(userEmail, new TreeMap<>());
    }

    @Override
    public TreeSet<Training> getTrainingsByUserEmailAndData(String userEmail, String trainingData) throws RepositoryException {
        TreeMap<String, TreeSet<Training>> userTrainings = userTrainingMap.get(userEmail);
        if (userTrainings != null) {
            TreeSet<Training> trainingsOnDate = userTrainings.get(trainingData);
            if (trainingsOnDate != null && !trainingsOnDate.isEmpty()) {
                return trainingsOnDate;
            }
        }
        throw new RepositoryException("Тренировка с датой " + trainingData + " не найдена для пользователя с email " + userEmail);
    }

    @Override
    public Training getTrainingByUserEmailAndDataAndName(String userEmail, String trainingData, String trainingName) throws RepositoryException {
        TreeMap<String, TreeSet<Training>> userTrainings = userTrainingMap.get(userEmail);
        if (userTrainings != null) {
            TreeSet<Training> trainingsOnDate = userTrainings.get(trainingData);
            if (trainingsOnDate != null && !trainingsOnDate.isEmpty()) {
                for (Training training : trainingsOnDate) {
                    if (training.getName().equals(trainingName)) {
                        return training;
                    }
                }
                throw new RepositoryException("Тренировка с именем " + trainingName + " не найдена в тренировках с датой " + trainingData + " для пользователя с email " + userEmail);
            } else {
                throw new RepositoryException("На указанную дату " + trainingData + " нет тренировок для пользователя с email " + userEmail);
            }
        } else {
            throw new RepositoryException("Пользователь с email " + userEmail + " не найден в тренировках");
        }
    }



    @Override
    public void saveTraining(String userEmail, Training newTraining) throws RepositoryException {
        TreeMap<String, TreeSet<Training>> userTrainings = userTrainingMap.computeIfAbsent(userEmail, k -> new TreeMap<>());
        TreeSet<Training> trainingsOnDate = userTrainings.computeIfAbsent(newTraining.getDate(), k -> new TreeSet<>());
        if (!trainingsOnDate.add(newTraining)){
            throw new RepositoryException("Тренировка с именем " + newTraining.getName() + " на дату " + newTraining.getDate() + " уже существует");
        } else{
            userTrainings.put(newTraining.getDate(), trainingsOnDate);
        }

    }

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

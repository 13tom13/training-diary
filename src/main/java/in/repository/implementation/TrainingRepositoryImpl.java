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
        TreeSet<Training> trainingsOnDate = userTrainings.computeIfAbsent(String.valueOf(newTraining.getDate()), k -> new TreeSet<>());

        if (!trainingsOnDate.add(newTraining)){
            throw new RepositoryException("Тренировка с именем " + newTraining.getName() + " на дату " + newTraining.getDate() + " уже существует");
        } else{
            userTrainings.put(String.valueOf(newTraining.getDate()), trainingsOnDate);
        }

    }

    @Override
    public void addTrainingAdditional(Training training, String additionalName, String additionalValue) throws RepositoryException {
        if (!training.getAdditions().containsKey(additionalName)){
            training.addAdditional(additionalName, additionalValue);
        } else {
            throw new RepositoryException("Дополнительное поле с именем " + additionalName + " уже существует");
        }

    }

    @Override
    public void removeTrainingAdditional (Training training, String additionalName) throws RepositoryException {
        if (training.getAdditions().containsKey(additionalName)){
            training.getAdditions().remove(additionalName);
        } else {
            throw new RepositoryException("дополнительная информация с именем " + additionalName + " не найдена");
        }

    }

    @Override
    public void changeNameTraining(Training training, String newName) {
        training.setName(newName);
    }

    @Override
    public void changeDateTraining(Training training, String newDate) {
        training.setDate(newDate);
    }

    @Override
    public void changeDurationTraining(Training training, int newDuration) {
        training.setDuration(newDuration);
    }

    @Override
    public void changeCaloriesTraining(Training training, int newCalories) {
        training.setCaloriesBurned(newCalories);
    }
}

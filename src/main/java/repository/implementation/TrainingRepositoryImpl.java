package repository.implementation;

import exception.RepositoryException;
import model.Training;
import repository.TrainingRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static model.Training.DATE_FORMAT;

public class TrainingRepositoryImpl implements TrainingRepository {

    private final Map<String, TreeSet<Training>> userTrainingMap = new HashMap<>();

    @Override
    public TreeSet<Training> getAllTrainingsFromUser(String userEmail) {
        return userTrainingMap.get(userEmail);
    }

    @Override
    public Training getTrainingByNumber(String userEmail, int trainingNumber) throws RepositoryException {
        TreeSet<Training> userTrainings = userTrainingMap.get(userEmail);
        if (userTrainings != null && trainingNumber >= 0 && trainingNumber < userTrainings.size()) {
            // Получение тренировки по номеру из TreeSet, который автоматически сортирует тренировки по дате
            return new ArrayList<>(userTrainings).get(trainingNumber);
        } else {
            throw new RepositoryException("Тренировка с номером " + trainingNumber + " не найдена для пользователя с email " + userEmail);
        }
    }

    @Override
    public Training createTraining(String userEmail, String name, String date, int duration, int caloriesBurned) {
        if (!userTrainingMap.containsKey(userEmail)) {
            userTrainingMap.put(userEmail, new TreeSet<>());
        }

        TreeSet<Training> userTrainings = userTrainingMap.get(userEmail);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        for (Training training : userTrainings) {
            try {
                if (training.getName().equals(name) && training.getDate().equals(dateFormat.parse(date))) {
                    throw new RepositoryException("Тренировка с именем " + name + " на дату " + date + " уже существует");
                }
            } catch (ParseException e) {
                System.out.println("Не удалось распознать дату " + date);
            } catch (RepositoryException e) {
                System.err.println(e.getMessage());
            }
        }
        Training newTraining = new Training(name, date, duration, caloriesBurned);
        userTrainings.add(newTraining);
        return newTraining;
    }

    @Override
    public void addTrainingAdditional(Training training, String additionalName, String additionalValue) {
        training.addAdditional(additionalName, additionalValue);
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

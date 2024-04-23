package in.repository.trainingtype.implementation;

import in.repository.trainingtype.TrainingTypeRepository;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainingTypeRepositoryCollections implements TrainingTypeRepository {
    private final Map<Long, List<String>> trainingTypes;

    private final long DEFAULT_TRAINING_TYPE_ID = -1L;

    /**
     * Конструктор класса TrainingTypeRepositoryCollections.
     * Инициализирует карту trainingTypes и добавляет список тренировок по умолчанию.
     */
    public TrainingTypeRepositoryCollections() {
        trainingTypes = new HashMap<>();
        addDefaultTrainingTypes();
    }

    /**
     * Получает список тренировок для указанного пользователя.
     * Если у пользователя нет собственного списка, возвращает список по умолчанию.
     *
     * @param user пользователь
     * @return список тренировок пользователя
     */
    @Override
    public List<String> getTrainingTypes(User user) {
        List<String> userTrainingTypes = trainingTypes.get(user.getId());
        if (userTrainingTypes == null) {
            return new ArrayList<>(trainingTypes.get(DEFAULT_TRAINING_TYPE_ID));
        } else {
            return new ArrayList<>(userTrainingTypes);
        }
    }

    /**
     * Добавляет тип тренировки для указанного пользователя.
     * Если у пользователя еще нет списка тренировок, создает его на основе списка по умолчанию.
     *
     * @param user         пользователь
     * @param trainingType тип тренировки для добавления
     */
    @Override
    public void saveTrainingType(User user, String trainingType) {
        List<String> userTrainingTypes = trainingTypes.computeIfAbsent(user.getId(),
                k -> new ArrayList<>(trainingTypes.getOrDefault(DEFAULT_TRAINING_TYPE_ID, List.of())));
        userTrainingTypes.add(trainingType);
    }

    /**
     * Добавляет список тренировок по умолчанию.
     */
    private void addDefaultTrainingTypes() {
        List<String> defaultTrainingTypes = new ArrayList<>();
        defaultTrainingTypes.add("Кардио");
        defaultTrainingTypes.add("Силовая");
        trainingTypes.put(DEFAULT_TRAINING_TYPE_ID, defaultTrainingTypes);
    }
}

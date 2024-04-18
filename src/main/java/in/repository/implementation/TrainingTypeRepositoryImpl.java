package in.repository.implementation;

import in.repository.TrainingTypeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация интерфейса {@link TrainingTypeRepository} для хранения типов тренировок.
 */
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {
    private final Map<String, List<String>> trainingTypes;

    /**
     * Конструктор класса TrainingTypeRepositoryImpl.
     * Инициализирует карту trainingTypes и добавляет список тренировок по умолчанию.
     */
    public TrainingTypeRepositoryImpl() {
        trainingTypes = new HashMap<>();
        addDefaultTrainingTypes();
    }

    /**
     * Получает список тренировок для указанного пользователя.
     * Если у пользователя нет собственного списка, возвращает список по умолчанию.
     *
     * @param userEmail электронная почта пользователя
     * @return список тренировок пользователя
     */
    @Override
    public List<String> getTrainingTypes(String userEmail) {
        List<String> userTrainingTypes = trainingTypes.get(userEmail);
        if (userTrainingTypes == null) {
            trainingTypes.put(userEmail, trainingTypes.get("default"));
            return trainingTypes.get("default");
        } else {
            return userTrainingTypes;
        }
    }

    /**
     * Добавляет тип тренировки для указанного пользователя.
     * Если у пользователя еще нет списка тренировок, создает его на основе списка по умолчанию.
     *
     * @param userEmail    электронная почта пользователя
     * @param trainingType тип тренировки для добавления
     */
    @Override
    public void saveTrainingType(String userEmail, String trainingType) {
        List<String> userTrainingTypes = trainingTypes.computeIfAbsent
                (userEmail, k -> new ArrayList<>(trainingTypes.getOrDefault("default", List.of())));
        userTrainingTypes.add(trainingType);
    }

    /**
     * Добавляет список тренировок по умолчанию.
     */
    public void addDefaultTrainingTypes() {
        List<String> defaultTrainingTypes = new ArrayList<>();
        defaultTrainingTypes.add("Кардио");
        defaultTrainingTypes.add("Силовая");
        trainingTypes.put("default", defaultTrainingTypes);
    }

}

package entities.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

import static utils.Utils.getStringFromDate;

public class TrainingDTO implements Comparable<TrainingDTO> {

    private Long id;

    @NotBlank(message = "Имя тренировки не должно быть пустым")
    private String name;

    @NotNull(message = "Дата тренировки не должна быть пустой")
    @PastOrPresent(message = "Дата тренировки должна быть в прошлом или настоящем")
    private LocalDate date;

    @Positive(message = "Продолжительность тренировки должна быть положительной")
    private int duration;

    @PositiveOrZero(message = "Сожженные калории должны быть неотрицательными")
    private int caloriesBurned;

    private HashMap<String, String> additions;

    public TrainingDTO() {
        this.additions = new HashMap<>();
    }

    public TrainingDTO(String name, LocalDate date, int duration, int caloriesBurned, HashMap<String, String> additions) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.additions = additions;
    }

    public TrainingDTO(String name, LocalDate date, int duration, int caloriesBurned) {
        this(name, date, duration, caloriesBurned, new HashMap<>());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public HashMap<String, String> getAdditions() {
        return additions;
    }

    public void setAdditions(HashMap<String, String> additions) {
        this.additions = additions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingDTO training = (TrainingDTO) o;
        return duration == training.duration &&
               caloriesBurned == training.caloriesBurned &&
               Objects.equals(name, training.name) &&
               Objects.equals(date, training.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, duration, caloriesBurned);
    }

    @Override
    public String toString() {
        // Форматирование даты в строку в формате "dd.MM.yy"
        String formattedDate = getStringFromDate(date);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Тренировка: ").append(name)
                .append(" | Дата: ").append(formattedDate) // Вывод отформатированной даты
                .append(" | Продолжительность: ").append(duration).append(" мин")
                .append(" | Сожжено калорий: ").append(caloriesBurned).append(" kcal");

        if (!additions.isEmpty()) {
            stringBuilder.append("\nДополнительная информация:");
            for (String key : additions.keySet()) {
                stringBuilder.append("\n").append(key).append(": ").append(additions.get(key));
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(TrainingDTO other) {
        int dateComparison = this.date.compareTo(other.date);
        if (dateComparison != 0) {
            return dateComparison;
        }
        return this.name.compareTo(other.name);
    }
}


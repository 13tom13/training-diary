package entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

import static utils.Utils.getStringFromDate;

@Data
@Builder
public class TrainingDTO implements Comparable<TrainingDTO> {

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

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
        this.additions = (additions == null) ? new HashMap<>() : additions;
    }

    public TrainingDTO(long id, String name, LocalDate date, int duration, int caloriesBurned, HashMap<String, String> additions) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.additions = (additions == null) ? new HashMap<>() : additions;
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

        if (additions != null && !additions.isEmpty()) {
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


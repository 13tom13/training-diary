package entity.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

import static utils.Utils.getStringFromDate;

/**
 * Класс, представляющий тренировку.
 */
@Data
@Builder
public class Training implements Comparable<Training> {
    private long id;
    private String name; // Название тренировки
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date; // Дата тренировки
    private int duration; // Продолжительность тренировки в минутах
    private int caloriesBurned; // Количество сожженных калорий

    private HashMap<String, String> additions; // Дополнительная информация о тренировке

    /**
     * Конструктор по умолчанию.
     */
    public Training() {
        this.additions = new HashMap<>();
    }

    /**
     * Конструктор с параметрами (без дополнительной информации).
     *
     * @param name           Название тренировки
     * @param date           Дата тренировки
     * @param duration       Продолжительность тренировки в минутах
     * @param caloriesBurned Количество сожженных калорий
     */
    public Training(String name, LocalDate date, int duration, int caloriesBurned) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.additions = new HashMap<>();
    }

    public Training(long id, String name, LocalDate date, int duration, int caloriesBurned, HashMap<String, String> additions) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.additions = (additions == null) ? new HashMap<>() : additions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

   /**
     * Добавляет дополнительные данные к тренировке.
     *
     * @param additionalName  Название дополнительной информации
     * @param additionalValue Значение дополнительной информации
     */
    public void addAdditional(String additionalName, String additionalValue) {
        additions.put(additionalName, additionalValue);
    }

    /**
     * Удаляет дополнительные данные из тренировки.
     *
     * @param additionalName Название дополнительной информации для удаления
     */
    public void removeAdditional(String additionalName) {
        additions.remove(additionalName);
    }

    /**
     * Сравнивает эту тренировку с другой тренировкой по дате, а затем по названию, если даты равны.
     *
     * @param other Другая тренировка для сравнения
     * @return Результат сравнения
     */
    @Override
    public int compareTo(Training other) {
        int dateComparison = this.date.compareTo(other.date);
        if (dateComparison != 0) {
            return dateComparison;
        }
        return this.name.compareTo(other.name);
    }

    /**
     * Проверяет, равны ли две тренировки.
     *
     * @param o Объект для сравнения
     * @return {@code true}, если тренировки равны, в противном случае {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return duration == training.duration && caloriesBurned == training.caloriesBurned && Objects.equals(name, training.name) && Objects.equals(date, training.date);
    }

    /**
     * Получает хэш-код тренировки.
     *
     * @return Хэш-код тренировки
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, date, duration, caloriesBurned);
    }

    /**
     * Возвращает строковое представление тренировки.
     *
     * @return Строковое представление тренировки
     */
    @Override
    public String toString() {
        // Форматирование даты в строку в формате "dd.MM.yy"
        String formattedDate = getStringFromDate(date);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Тренировка: ").append(name).append(" | Дата: ").append(formattedDate) // Вывод отформатированной даты
                .append(" | Продолжительность: ").append(duration).append(" мин").append(" | Сожжено калорий: ").append(caloriesBurned).append(" kcal");

        if (!additions.isEmpty()) {
            stringBuilder.append("\nДополнительная информация:");
            for (String key : additions.keySet()) {
                stringBuilder.append("\n").append(key).append(": ").append(additions.get(key));
            }
        }
        return stringBuilder.toString();
    }
}

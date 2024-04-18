package model;

import java.util.HashMap;
import java.util.Objects;

/**
 * Класс, представляющий тренировку.
 */
public class Training implements Comparable<Training> {

    private String name; // Название тренировки
    private String date; // Дата тренировки
    private int duration; // Продолжительность тренировки в минутах
    private int caloriesBurned; // Количество сожженных калорий

    private final HashMap<String, String> additions; // Дополнительная информация о тренировке

    /**
     * Конструктор по умолчанию.
     */
    public Training() {
        this.additions = new HashMap<>();
    }

    /**
     * Конструктор с параметрами.
     *
     * @param name           Название тренировки
     * @param date           Дата тренировки
     * @param duration       Продолжительность тренировки в минутах
     * @param caloriesBurned Количество сожженных калорий
     * @param additions      Дополнительная информация о тренировке
     */
    public Training(String name, String date, int duration, int caloriesBurned, HashMap<String, String> additions) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.additions = additions;
    }

    /**
     * Конструктор с параметрами (без дополнительной информации).
     *
     * @param name           Название тренировки
     * @param date           Дата тренировки
     * @param duration       Продолжительность тренировки в минутах
     * @param caloriesBurned Количество сожженных калорий
     */
    public Training(String name, String date, int duration, int caloriesBurned) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.additions = new HashMap<>();
    }

    /**
     * Устанавливает дату тренировки.
     *
     * @param date Дата тренировки в формате строки
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Получает дату тренировки.
     *
     * @return Дата тренировки
     */
    public String getDate() {
        return date;
    }

    /**
     * Получает название тренировки.
     *
     * @return Название тренировки
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название тренировки.
     *
     * @param name Новое название тренировки
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получает продолжительность тренировки в минутах.
     *
     * @return Продолжительность тренировки в минутах
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Устанавливает продолжительность тренировки в минутах.
     *
     * @param duration Новая продолжительность тренировки в минутах
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Получает количество сожженных калорий.
     *
     * @return Количество сожженных калорий
     */
    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    /**
     * Устанавливает количество сожженных калорий.
     *
     * @param caloriesBurned Новое количество сожженных калорий
     */
    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    /**
     * Получает дополнительную информацию о тренировке.
     *
     * @return Дополнительная информация о тренировке в виде HashMap
     */
    public HashMap<String, String> getAdditions() {
        return additions;
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
        return duration == training.duration &&
                caloriesBurned == training.caloriesBurned &&
                Objects.equals(name, training.name) &&
                Objects.equals(date, training.date);
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Тренировка: ").append(name)
                .append(" | Дата: ").append(date)
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
}

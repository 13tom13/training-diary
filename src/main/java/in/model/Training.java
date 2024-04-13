package in.model;

import java.util.HashMap;
import java.util.Objects;

public class Training implements Comparable<Training> {

    private String name;
    private String date;
    private int duration;
    private int caloriesBurned;

    private final HashMap<String, String> additions;

    public Training() {
        this.additions = new HashMap<>();
    }

    public Training(String name, String date, int duration, int caloriesBurned, HashMap<String, String> additions) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.additions = additions;
    }

    public Training(String name, String date, int duration, int caloriesBurned) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.additions = new HashMap<>();
    }


    public void setDate(String dateString) {
        this.date = dateString;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void addAdditional(String additionalName, String additionalValue) {
        additions.put(additionalName, additionalValue);
    }

    public void removeAdditional(String additionalName) {
        additions.remove(additionalName);
    }



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


    @Override
    public int compareTo(Training other) {
        int dateComparison = this.date.compareTo(other.date);
        if (dateComparison != 0) {
            return dateComparison;
        }

        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return Objects.equals(name, training.name) && Objects.equals(date, training.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }
}

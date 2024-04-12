package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Training implements Comparable<Training> {

    public static final String DATE_FORMAT = "dd.MM.yy";

    private String name;
    private Date date;
    private int duration;
    private int caloriesBurned;

    private final HashMap<String, String> additions = new HashMap<>();

    public Training(String name, String date, int duration, int caloriesBurned) {
        this.name = name;
        setDate(date);
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }

    public void setDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            this.date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Не удалось распознать дату " + dateString);
        }
    }

    public Date getDate() {
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

    @Override
    public String toString() {
        return "Training: " + name + " date: " + date + "duration: "
                + duration + " min" + " calories burned: " + caloriesBurned + " kcal";
    }

    @Override
    public int compareTo(Training other) {
        return this.date.compareTo(other.date);
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

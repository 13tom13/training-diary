package in.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Rights> rights;
    private boolean isActive = true;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.rights = new ArrayList<>(Arrays.asList(Rights.values()));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Rights> getRights() {
        return rights;
    }

    public void removeRights(String rightForRemove) {
        try {
            Rights rightToRemove = Rights.valueOf(rightForRemove);
            rights.remove(rightToRemove);
        } catch (IllegalArgumentException e) {
            System.out.println("Право " + rightForRemove + " не существует.");
        }
    }

    public void setRights(List<Rights> rights) {
        this.rights = rights;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        String status = isActive ? "active" : "deactivate";
        return "Пользователь: " + firstName + " " + lastName + " | email: " + email + " | права: " + rights.toString() + " | (" + status + ")";
    }
}

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

    private final List<Roles> roles;

    private boolean isActive = true;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.rights = new ArrayList<>(Arrays.asList(Rights.values()));
        this.roles = new ArrayList<>(List.of(Roles.USER));
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

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(String rolesForSet) {
        try {
            Roles roleForSet = Roles.valueOf(rolesForSet);
            roles.add(roleForSet);
        } catch (IllegalArgumentException e) {
            System.out.println("Роль " + rolesForSet + " не найдена");
        }
    }

    public void setRights(List<Rights> rightsForSet) {
        this.rights = rightsForSet;
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
        return "Пользователь: " + firstName + " " + lastName + " | email: " + email + " | роли: "
                + roles.toString()  +  " | права: " + rights.toString() + " | (" + status + ")";
    }
}

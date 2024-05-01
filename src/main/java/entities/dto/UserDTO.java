package entities.dto;

import entities.model.Rights;
import entities.model.Roles;

import java.util.List;

/**
 * DTO для передачи данных о пользователе.
 */
public class UserDTO {

    private long id; // Идентификатор пользователя
    private String firstName; // Имя пользователя
    private String lastName; // Фамилия пользователя
    private String email; // Электронная почта пользователя
    private List<Rights> rights; // Список идентификаторов прав пользователя
    private List<Roles> roles; // Список идентификаторов ролей пользователя
    private boolean isActive; // Активность пользователя

    // Конструкторы, геттеры и сеттеры

    public UserDTO() {
    }

    public UserDTO(long id, String firstName, String lastName, String email, List<Rights> rights, List<Roles> roles, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.rights = rights;
        this.roles = roles;
        this.isActive = isActive;
    }


    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<Rights> getRights() {
        return rights;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public boolean isActive() {
        return isActive;
    }


    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", rights=" + rights +
               ", roles=" + roles +
               ", isActive=" + isActive +
               '}';
    }
}

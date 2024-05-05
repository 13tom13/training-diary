package entities.dto;

import entities.model.Rights;
import entities.model.Roles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * DTO для передачи данных о пользователе.
 */
public class UserDTO {

    private long id;

    @NotEmpty(message = "Имя не должно быть пустым")
    private String firstName;

    @NotEmpty(message = "Фамилия не должна быть пустой")
    private String lastName;

    @Email(message = "Некорректный адрес электронной почты")
    private String email;

    @NotNull(message = "Список прав не должен быть пустым")
    private List<Rights> rights;

    @NotNull(message = "Список ролей не должен быть пустым")
    private List<Roles> roles;

    private boolean isActive;

        // Конструкторы, геттеры и сеттеры

        public UserDTO() {
        }

        public UserDTO(String email) {
            this.email = email;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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

    public List<Rights> getRights() {
        return rights;
    }

    public void setRights(List<Rights> rights) {
        this.rights = rights;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        String status = isActive ? "active" : "deactivate";
        return "Пользователь: " + firstName + " " + lastName + " | email: " + email + " | роли: "
               + roles.toString() + " | права: " + rights.toString() + " | (" + status + ")";
    }
}

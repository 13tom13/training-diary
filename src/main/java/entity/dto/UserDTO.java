package entity.dto;

import entity.model.Rights;
import entity.model.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO для передачи данных о пользователе.
 */
@Data
@Builder
public class UserDTO {

    private long id;

    @NotEmpty(message = "Имя не должно быть пустым")
    private String firstName;

    @NotEmpty(message = "Фамилия не должна быть пустой")
    private String lastName;

    @Email(message = "Некорректный адрес электронной почты")
    private String email;

    private List<Roles> roles = new ArrayList<>();

    private boolean isActive;

    public UserDTO() {
    }

    public UserDTO(long id, String firstName, String lastName, String email, List<Roles> roles, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
        this.isActive = isActive;
    }


    @Override
    public String toString() {
        String status = isActive ? "active" : "deactivate";
        return "Пользователь: " + firstName + " " + lastName + " | email: " + email + " | (" + status + ")";
    }
}

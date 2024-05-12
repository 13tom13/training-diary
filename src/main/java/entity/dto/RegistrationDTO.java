package entity.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для передачи данных о регистрации нового пользователя.
 */
@Data
@Builder
public class RegistrationDTO {

    @NotBlank(message = "Электронная почта не должна быть пустой")
    @Email(message = "Неправильный формат электронной почты")
    private String email;

    @NotBlank(message = "Имя не должно быть пустым")
    private String firstName;

    @NotBlank(message = "Фамилия не должна быть пустой")
    private String lastName;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 3, message = "Пароль должен содержать как минимум 3 символов")
    private String password;

    @Override
    public String toString() {
        return "RegistrationDTO{" +
               "email='" + email + '\'' +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}

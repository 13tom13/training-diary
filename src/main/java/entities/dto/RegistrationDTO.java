package entities.dto;

import jakarta.validation.constraints.*;

/**
 * DTO для передачи данных о регистрации нового пользователя.
 */
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

    public RegistrationDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Геттеры и сеттеры

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

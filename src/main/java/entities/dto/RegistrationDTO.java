package entities.dto;

/**
 * DTO для передачи данных о регистрации нового пользователя.
 */
public class RegistrationDTO {

    private String email; // Электронная почта пользователя
    private String firstName; // Имя пользователя
    private String lastName; // Фамилия пользователя
    private String password; // Пароль пользователя

    // Конструкторы
    public RegistrationDTO() {
    }

    public RegistrationDTO(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
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

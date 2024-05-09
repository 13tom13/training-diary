package entity.dto;

import jakarta.validation.constraints.*;

public class AuthorizationDTO {

    @NotBlank(message = "Электронная почта не должна быть пустой")
    @Email(message = "Неправильный формат электронной почты")
    private String email;

    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;

    public AuthorizationDTO() {
    }

    public AuthorizationDTO(String email, String password) {
        this.email = email;
        this.password = password;
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
}

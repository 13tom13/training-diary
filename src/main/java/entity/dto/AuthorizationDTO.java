package entity.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationDTO {

    @NotBlank(message = "Электронная почта не должна быть пустой")
    @Email(message = "Неправильный формат электронной почты")
    private String email;

    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;
}

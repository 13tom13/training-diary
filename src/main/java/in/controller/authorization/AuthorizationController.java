package in.controller.authorization;

import entity.dto.AuthorizationDTO;
import exceptions.security.AuthorizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Интерфейс контроллера авторизации.
 */
public interface AuthorizationController {

    /**
     * Метод для входа пользователя в систему.
     *
     * @param authorizationDTO@return объект пользователя, который вошел в систему
     * @throws AuthorizationException если произошла ошибка авторизации
     */
    ResponseEntity<?> login(AuthorizationDTO authorizationDTO) throws AuthorizationException, IOException;

    @GetMapping("/getUserRoles")
    ResponseEntity<?> login(@RequestParam Long userId);
}

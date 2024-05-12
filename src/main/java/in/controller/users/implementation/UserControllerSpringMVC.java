package in.controller.users.implementation;

import entity.dto.RegistrationDTO;
import exceptions.RepositoryException;
import in.controller.users.UserController;
import in.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserControllerSpringMVC implements UserController {

    private final UserService userService;

    @Override
    @PostMapping("/registration")
    public ResponseEntity<String> createNewUser(@RequestBody RegistrationDTO registrationDTO) {
        // Сохраняем нового пользователя
        try {
            userService.saveUser(registrationDTO);
            String response = "Пользователь с электронной почтой: " + registrationDTO.getEmail() + " успешно сохранен";
            return ResponseEntity.ok(response);
        } catch (RepositoryException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

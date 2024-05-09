package in.controller.authorization.implementation;

import entity.dto.AuthorizationDTO;
import entity.dto.UserDTO;
import exceptions.security.AuthorizationException;
import in.controller.authorization.AuthorizationController;
import in.service.users.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorizationControllerSpringMVC implements AuthorizationController {

    private final AuthorizationService authorizationService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthorizationDTO authorizationDTO) {
        String email = authorizationDTO.getEmail();
        String password = authorizationDTO.getPassword();
        try {
            UserDTO userDTO = authorizationService.login(email, password);
            return ResponseEntity.ok(userDTO);
        } catch (AuthorizationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

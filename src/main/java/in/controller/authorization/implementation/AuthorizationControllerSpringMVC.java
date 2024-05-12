package in.controller.authorization.implementation;

import entity.dto.AuthorizationDTO;
import entity.dto.UserDTO;
import entity.model.Roles;
import exceptions.security.AuthorizationException;
import in.controller.authorization.AuthorizationController;
import in.service.users.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

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

    @Override
    @GetMapping("/getUserRoles")
    public ResponseEntity<?> login(@RequestParam("userId")  Long userId) {
        try {
            List<Roles> roles = authorizationService.getUserRoles(userId);
            return ResponseEntity.ok(roles);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

package in.controller.users.implementation;

import entity.dto.UserDTO;
import entity.model.Rights;
import entity.model.User;
import exceptions.RepositoryException;
import in.controller.users.AdminController;
import in.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.mappers.UserMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminControllerSpringMVC implements AdminController {

    private final UserRepository userRepository;
    
    private final UserMapper userMapper;

    @Override
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userRepository.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @Override
    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUser(@RequestParam("email") String email) {
        Optional<User> userByEmail = userRepository.getUserByEmail(email);
        if (userByEmail.isPresent()) {
            UserDTO userDTO = userMapper.userToUserDTO(userByEmail.get());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PutMapping("/user/change/username")
    public ResponseEntity<UserDTO> changeUserName(@RequestBody UserDTO userDTO, String newName) throws RepositoryException {
        Optional<User> userByEmail = userRepository.getUserByEmail(userDTO.getEmail());
        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
            user.setFirstName(newName);
            userRepository.updateUser(user);
            return ResponseEntity.ok(userMapper.userToUserDTO(user));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PutMapping("/user/change/lastname")
    public ResponseEntity<UserDTO> changeUserLastName(@RequestBody UserDTO userDTO, String newLastName) throws RepositoryException {
        Optional<User> userByEmail = userRepository.getUserByEmail(userDTO.getEmail());
        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
            user.setLastName(newLastName);
            userRepository.updateUser(user);
            return ResponseEntity.ok(userMapper.userToUserDTO(user));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PutMapping("/user/change/password")
    public ResponseEntity<UserDTO> changeUserPassword(@RequestBody UserDTO userDTO, String newPassword) throws RepositoryException {
        Optional<User> userByEmail = userRepository.getUserByEmail(userDTO.getEmail());
        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
            user.setPassword(newPassword);
            userRepository.updateUser(user);
            return ResponseEntity.ok(userMapper.userToUserDTO(user));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PutMapping("/user/change/active")
    public ResponseEntity<UserDTO> changeUserActive(@RequestBody UserDTO userDTO) throws RepositoryException {
        Optional<User> userByEmail = userRepository.getUserByEmail(userDTO.getEmail());
        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
            user.setActive(!user.isActive());
            userRepository.updateUser(user);
            return ResponseEntity.ok(userMapper.userToUserDTO(user));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PutMapping("/user/change/rights")
    public ResponseEntity<UserDTO> changeUserRights(@RequestBody UserDTO userDTO, List<Rights> userRights) throws RepositoryException {
        Optional<User> userByEmail = userRepository.getUserByEmail(userDTO.getEmail());
        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
            user.setRights(userRights);
            userRepository.updateUser(user);
            return ResponseEntity.ok(userMapper.userToUserDTO(user));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @DeleteMapping("user/delete")
    public ResponseEntity<Void> deleteUser(@RequestParam("user") UserDTO userDTO) {
        Optional<User> userByEmail = userRepository.getUserByEmail(userDTO.getEmail());
        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
            userRepository.deleteUser(user);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Override
    @GetMapping("/rights")
    public ResponseEntity<List<Rights>> getAllRights() throws IOException {
        List<Rights> allRights = userRepository.getAllRights();
        return ResponseEntity.ok(allRights);
    }
}

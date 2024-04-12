package in.controller;

import in.exception.RepositoryException;
import in.exception.ValidationException;
import in.service.UserService;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void createNewUser(String firstName, String lastName, String email, String password) {
        try {
            userService.saveUser(firstName, lastName, email, password);
            System.out.println("User with email " + email + " created");
        } catch (ValidationException | RepositoryException e) {
            System.err.println(e.getMessage());
        }

    }
}

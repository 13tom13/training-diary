package controller;

import exception.AuthorizationException;
import exception.RepositoryException;
import exception.ServiceException;
import exception.ValidationException;
import service.UserService;

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

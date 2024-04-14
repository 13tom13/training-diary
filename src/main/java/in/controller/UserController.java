package in.controller;

import in.exception.RepositoryException;
import in.exception.ValidationException;
import in.logger.Logger;
import in.service.UserService;

public class UserController {

    private final UserService userService;

    private static final Logger logger = Logger.getInstance();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void createNewUser(String firstName, String lastName, String email, String password) {
        try {
            userService.saveUser(firstName, lastName, email, password);
            logger.logAction(email,"created");
        } catch (ValidationException | RepositoryException e) {
            System.err.println(e.getMessage());
        }
    }
}

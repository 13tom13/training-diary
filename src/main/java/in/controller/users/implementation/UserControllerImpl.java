package in.controller.users.implementation;

import in.controller.users.UserController;
import exceptions.RepositoryException;
import exceptions.ValidationException;
import utils.Logger;
import in.service.users.UserService;

public class UserControllerImpl implements UserController {

    private final UserService userService;

    private final Logger logger = Logger.getInstance();

    public UserControllerImpl(UserService userService) {
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

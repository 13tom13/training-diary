package in.controller;

import in.exception.security.AuthorizationException;
import in.logger.Logger;
import in.model.User;
import in.service.AuthorizationService;



public class AuthorizationController {

    private final AuthorizationService authorizationService;

    private static final Logger logger = Logger.getInstance();

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }


    public User login(String email, String password) throws AuthorizationException {
        User user = authorizationService.login(email, password);
        logger.logAction(email,"logged in");
        return user;
    }
}

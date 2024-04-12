package in.controller;

import in.exception.ServiceException;
import in.exception.AuthorizationException;
import in.model.User;
import in.service.AuthorizationService;

public class AuthorizationController {

private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public User login(String email, String password) throws AuthorizationException, ServiceException {
        User user = authorizationService.login(email, password);
        System.out.println("User with email " + email + " logged in");
        return user;
    }

    public void logout() {


    }

}

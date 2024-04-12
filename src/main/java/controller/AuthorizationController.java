package controller;

import exception.ServiceException;
import exception.AuthorizationException;
import model.User;
import service.AuthorizationService;

import java.util.Optional;

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

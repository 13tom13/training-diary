package service;

import exception.AuthorizationException;
import model.User;

public interface AuthorizationService {
    User login(String email, String password) throws AuthorizationException;
}

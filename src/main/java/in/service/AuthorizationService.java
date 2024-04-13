package in.service;

import in.exception.security.AuthorizationException;
import in.exception.security.NotActiveUserException;
import in.model.User;

public interface AuthorizationService {
    User login(String email, String password) throws AuthorizationException;
}

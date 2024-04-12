package in.service;

import in.exception.AuthorizationException;
import in.model.User;

public interface AuthorizationService {
    User login(String email, String password) throws AuthorizationException;
}

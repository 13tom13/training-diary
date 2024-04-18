package in.service.users;

import exceptions.security.AuthorizationException;
import model.User;

public interface AuthorizationService {
    User login(String email, String password) throws AuthorizationException;
}

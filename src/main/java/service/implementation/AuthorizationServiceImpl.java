package service.implementation;

import exception.AuthorizationException;
import model.User;
import repository.UserRepository;
import service.AuthorizationService;

import java.util.Optional;

public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserRepository userRepository;

    public AuthorizationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String email, String password) throws AuthorizationException {
        Optional<User> user = userRepository.getUserByEmail(email);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                return user.get();
            } else {
                throw new AuthorizationException("Wrong password");
            }
        } else {
            throw new AuthorizationException("User not found");
        }
    }
}

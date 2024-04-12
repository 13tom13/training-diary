package in.service.implementation;

import in.exception.AuthorizationException;
import in.model.User;
import in.repository.UserRepository;
import in.service.AuthorizationService;

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

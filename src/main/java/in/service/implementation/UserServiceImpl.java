package in.service.implementation;

import in.exception.RepositoryException;
import in.exception.ServiceException;
import in.exception.ValidationException;
import in.model.User;
import in.service.UserService;
import in.repository.UserRepository;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmail(String email) throws ServiceException {
        return userRepository.getUserByEmail(email).orElseThrow(() ->
                new ServiceException("User not found"));
    }

    @Override
    public void saveUser(String firstName, String lastName, String email, String password)
            throws ValidationException, RepositoryException {
        if (firstName == null || firstName.isEmpty()) {
            throw new ValidationException("firstName");
        } else if (lastName == null || lastName.isEmpty()) {
            throw new ValidationException("lastName");
        } else if (email == null || email.isEmpty()) {
            throw new ValidationException("email");
        } else if (password == null || password.isEmpty()) {
            throw new ValidationException("password");
        } else {
            User user = new User(firstName, lastName, email, password);
            userRepository.saveUser(user);
            System.out.println("saved user: " + user.getEmail() + "\n");
        }
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(String email) {

    }
}

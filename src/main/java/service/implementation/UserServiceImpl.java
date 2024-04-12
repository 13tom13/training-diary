package service.implementation;

import exception.RepositoryException;
import exception.ServiceException;
import exception.ValidationException;
import model.User;
import repository.UserRepository;
import service.UserService;

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
            throw new ValidationException("field \"firstName\" cannot be empty", this.getClass());
        } else if (lastName == null || lastName.isEmpty()) {
            throw new ValidationException("field \"lastName\" cannot be empty", this.getClass());
        } else if (email == null || email.isEmpty()) {
            throw new ValidationException("field \"email\" cannot be empty", this.getClass());
        } else if (password == null || password.isEmpty()) {
            throw new ValidationException("field \"password\" cannot be empty", this.getClass());
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

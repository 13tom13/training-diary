package in.repository.implementation;

import in.exception.RepositoryException;
import in.model.User;
import in.repository.UserRepository;

import java.util.*;

public class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> userRepository = new HashMap<>();

    public UserRepositoryImpl() {
        System.out.println("Add test User\nEmail: test@mail.ru, Password: pass");
        User user = new User("Ivan", "Petrov", "test@mail.ru", "pass");
        userRepository.put(user.getEmail(), user);
    }


    @Override
    public Optional<User> getUserByEmail(String email) {
        User user = userRepository.get(email);
        return Optional.ofNullable(user);
    }

    @Override
    public void saveUser(User user) throws RepositoryException {
        if (!userRepository.containsKey(user.getEmail())) {
            userRepository.put(user.getEmail(), user);
        } else {
            throw new RepositoryException("User with email " + user.getEmail() + " already exists");
        }
    }

    @Override
    public void updateUser(User user) {
        userRepository.put(user.getEmail(), user);
    }

    public void deleteUser(String email) {
        userRepository.remove(email);
    }
}

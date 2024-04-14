package in.repository;

import in.exception.RepositoryException;
import in.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> getUserByEmail(String email);

    List<User> getAllUsers();

    void saveUser(User user) throws RepositoryException;

    void updateUser(User user);

    void deleteUser(String email);

}

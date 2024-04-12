package repository;

import exception.RepositoryException;
import model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> getUserByEmail (String email);

    void saveUser (User user) throws RepositoryException;

    void updateUser (User user);

    void deleteUser (String email);

}

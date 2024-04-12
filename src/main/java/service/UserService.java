package service;

import exception.RepositoryException;
import exception.ServiceException;
import exception.ValidationException;
import model.User;

public interface UserService {

    User getUserByEmail(String email) throws ServiceException;

    void saveUser(String firstName, String lastName, String email, String password) throws RepositoryException, ValidationException;

    void updateUser(User user);

    void deleteUser(String email);


}

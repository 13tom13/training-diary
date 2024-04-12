package in.service;

import in.exception.RepositoryException;
import in.exception.ServiceException;
import in.exception.ValidationException;
import in.model.User;

public interface UserService {

    User getUserByEmail(String email) throws ServiceException;

    void saveUser(String firstName, String lastName, String email, String password) throws RepositoryException, ValidationException;

    void updateUser(User user);

    void deleteUser(String email);


}

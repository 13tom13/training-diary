package in.controller.users;

import model.Rights;
import model.User;

import java.util.List;

public interface AdminController {
    List<User> getAllUsers();

    User getUser(String email);

    void changeUserName(User user, String newName);

    void changeUserLastName(User user, String newLastName);

    void changeUserPassword(User user, String newPassword);

    void changeUserActive(User user);

    void changeUserRights(User user, List<Rights> userRights);

    void deleteUser(User user);
}

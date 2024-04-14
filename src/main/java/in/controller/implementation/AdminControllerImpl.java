package in.controller.implementation;

import in.controller.AdminController;
import in.model.Rights;
import in.model.User;
import in.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class AdminControllerImpl implements AdminController {

    private final UserRepository userRepository;

    public AdminControllerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUser(String email) {
        Optional<User> userOptional = userRepository.getUserByEmail(email);
        return userOptional.orElse(null);
    }

    @Override
    public void changeUserName(User user, String newName) {
        user.setFirstName(newName);
        userRepository.updateUser(user);
    }

    @Override
    public void changeUserLastName(User user, String newLastName) {
        user.setLastName(newLastName);
        userRepository.updateUser(user);
    }

    @Override
    public void changeUserPassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.updateUser(user);
    }

    @Override
    public void changeUserActive(User user) {
        user.setActive(!user.isActive());
        userRepository.updateUser(user);
    }

    @Override
    public void changeUserRights(User user, List<Rights> userRights) {
        user.setRights(userRights);
        userRepository.updateUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.deleteUser(user.getEmail());
    }
}

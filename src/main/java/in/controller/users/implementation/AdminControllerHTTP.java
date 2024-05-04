package in.controller.users.implementation;

import entities.dto.UserDTO;
import entities.model.Rights;
import entities.model.User;
import exceptions.UserNotFoundException;
import in.controller.users.AdminController;

import java.util.List;

public class AdminControllerHTTP implements AdminController {
    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public UserDTO getUser(String email) throws UserNotFoundException {
        return null;
    }

    @Override
    public void changeUserName(UserDTO userDTO, String newName) {

    }

    @Override
    public void changeUserLastName(UserDTO userDTO, String newLastName) {

    }

    @Override
    public void changeUserPassword(UserDTO userDTO, String newPassword) {

    }

    @Override
    public void changeUserActive(UserDTO userDTO) {

    }

    @Override
    public void changeUserRights(UserDTO userDTO, List<Rights> userRights) {

    }

    @Override
    public void deleteUser(UserDTO userDTO) {

    }

    @Override
    public List<Rights> getAllRights() {
        return List.of();
    }
}

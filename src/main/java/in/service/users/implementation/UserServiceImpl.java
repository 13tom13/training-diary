package in.service.users.implementation;

import dto.UserDTO;
import exceptions.RepositoryException;
import exceptions.ServiceException;
import exceptions.ValidationException;
import model.User;
import in.service.users.UserService;
import in.repository.user.UserRepository;

import java.sql.SQLException;

/**
 * Реализация интерфейса {@link UserService}, предоставляющая методы для работы с пользователями.
 */
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Конструктор для создания экземпляра класса UserServiceImpl.
     *
     * @param userRepository репозиторий пользователей
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Получает пользователя по его электронной почте.
     *
     * @param email электронная почта пользователя
     * @return объект пользователя
     * @throws ServiceException если пользователь не найден
     */
    @Override
    public User getUserByEmail(String email) throws ServiceException {
        return userRepository.getUserByEmail(email).orElseThrow(() ->
                new ServiceException("User not found"));
    }

    /**
     * Сохраняет нового пользователя.
     *
     * @param userDTO объект UserDTO с данными нового пользователя
     * @throws ValidationException если одно из полей userDTO пустое или null
     * @throws RepositoryException если произошла ошибка доступа к репозиторию
     */
    @Override
    public void saveUser(UserDTO userDTO)
            throws ValidationException, RepositoryException {
        if (userDTO.getFirstName() == null || userDTO.getFirstName().isEmpty()) {
            throw new ValidationException("firstName");
        } else if (userDTO.getLastName() == null || userDTO.getLastName().isEmpty()) {
            throw new ValidationException("lastName");
        } else if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new ValidationException("email");
        } else if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new ValidationException("password");
        } else {
            User user = new User(userDTO.getFirstName(),
                    userDTO.getLastName(), userDTO.getEmail(), userDTO.getPassword());
            try {
                userRepository.assignUserRights(user);
                userRepository.assignUserRoles(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            userRepository.saveUser(user);
            System.out.println("saved user: " + user.getEmail() + "\n");
        }
    }
}

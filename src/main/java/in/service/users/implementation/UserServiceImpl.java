package in.service.users.implementation;

import exceptions.RepositoryException;
import exceptions.ServiceException;
import exceptions.ValidationException;
import model.User;
import in.service.users.UserService;
import in.repository.user.UserRepository;

/**
 * Реализация интерфейса {@link UserService}.
 * Предоставляет методы для работы с пользователями.
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
     * @param firstName имя пользователя
     * @param lastName  фамилия пользователя
     * @param email     электронная почта пользователя
     * @param password  пароль пользователя
     * @throws ValidationException если одно из полей пустое или null
     * @throws RepositoryException если произошла ошибка доступа к репозиторию
     */
    @Override
    public void saveUser(String firstName, String lastName, String email, String password)
            throws ValidationException, RepositoryException {
        if (firstName == null || firstName.isEmpty()) {
            throw new ValidationException("firstName");
        } else if (lastName == null || lastName.isEmpty()) {
            throw new ValidationException("lastName");
        } else if (email == null || email.isEmpty()) {
            throw new ValidationException("email");
        } else if (password == null || password.isEmpty()) {
            throw new ValidationException("password");
        } else {
            User user = new User(firstName, lastName, email, password);
            userRepository.saveUser(user);
            System.out.println("saved user: " + user.getEmail() + "\n");
        }
    }

}

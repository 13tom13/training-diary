package in.service.users.implementation;

import entities.dto.RegistrationDTO;
import entities.model.User;
import exceptions.RepositoryException;
import exceptions.ServiceException;
import in.repository.user.UserRepository;
import in.service.users.UserService;

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
                new ServiceException("Пользователь не найден"));
    }

    /**
     * Сохраняет нового пользователя.
     *
     * @param registrationDTO@throws ValidationException если одно из полей registrationDTO пустое или null
     * @throws RepositoryException если произошла ошибка доступа к репозиторию
     */
    @Override
    public void saveUser(RegistrationDTO registrationDTO)
            throws RepositoryException {
        User user = new User(registrationDTO.getFirstName(),
                registrationDTO.getLastName(), registrationDTO.getEmail(), registrationDTO.getPassword());
        userRepository.saveUser(user);
        System.out.println("Пользователь с электронной почтой: " + user.getEmail() + " успешно сохранен");
        System.out.println();
    }
}

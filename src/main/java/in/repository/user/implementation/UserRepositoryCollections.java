package in.repository.user.implementation;

import entity.model.User;
import exceptions.RepositoryException;
import in.repository.user.UserRepository;
import entity.model.Rights;
import entity.model.Roles;

import java.util.*;

/**
 * Реализация интерфейса {@link UserRepository}, предоставляющая методы для работы с хранилищем пользователей.
 */
public class UserRepositoryCollections implements UserRepository {

    /**
     * Хранилище пользователей, где ключами являются адреса электронной почты пользователей.
     */
    private final Map<String, User> userRepository;

    /**
     * Хранилище прав, где ключами являются идентификаторы прав.
     */
    private final Map<Long, Rights> rightsRepository;

    /**
     * Хранилище ролей, где ключами являются идентификаторы ролей.
     */
    private final Map<Long, Roles> rolesRepository;

    private static final long USER_ROLE_ID = 2L;


    /**
     * Конструктор по умолчанию, инициализирующий хранилище пользователей.
     */
    public UserRepositoryCollections() {
        userRepository = new HashMap<>();
        rightsRepository = new HashMap<>();
        rolesRepository = new HashMap<>();
        initializeDefaultRolesAndRights();
    }

    /**
     * Получает пользователя по его электронной почте.
     *
     * @param email электронная почта пользователя
     * @return объект Optional, содержащий пользователя, если пользователь найден, иначе пустой объект
     */
    @Override
    public Optional<User> getUserByEmail(String email) {
        User user = userRepository.get(email);
        return Optional.ofNullable(user);
    }

    /**
     * Получает список всех пользователей.
     *
     * @return список всех пользователей
     */
    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.values());
    }

    /**
     * Получает список всех прав.
     *
     * @return список всех прав
     */
    @Override
    public List<Rights> getAllRights() {
        return new ArrayList<>(rightsRepository.values());
    }

    /**
     * Сохраняет нового пользователя.
     *
     * @param user объект пользователя для сохранения
     * @throws RepositoryException если пользователь с такой электронной почтой уже существует
     */

    @Override
    public void saveUser(User user) throws RepositoryException {
        if (!userRepository.containsKey(user.getEmail())) {
            userRepository.put(user.getEmail(), user);
        } else {
            throw new RepositoryException("Пользователь с адресом электронной почты " + user.getEmail() + " уже существует");
        }
    }


    /**
     * Обновляет информацию о пользователе.
     *
     * @param user объект пользователя для обновления
     */
    @Override
    public void updateUser(User user) {
        userRepository.put(user.getEmail(), user);
    }

    /**
     * Удаляет пользователя.
     *
     * @param user объект пользователя для удаления
     */
    @Override
    public void deleteUser(User user) {
        userRepository.remove(user.getEmail());
    }

    /**
     * Присваивает пользователю все права из хранилища прав.
     *
     * @param user пользователь, которому нужно присвоить права
     */
    @Override
    public void assignUserRights(User user) {
        // Получаем все права из rightsRepository и присваиваем их пользователю
        user.setRights(new ArrayList<>(rightsRepository.values()));
    }

    /**
     * Присваивает пользователю роль "User" из хранилища ролей.
     *
     * @param user пользователь, которому нужно присвоить роль
     */
    @Override
    public void assignUserRoles(User user) {
        Roles userRole = rolesRepository.get(USER_ROLE_ID);
        if (userRole != null)
            user.setRoles(List.of(userRole));
    }

    /**
     * Метод для заполнения коллекций Rights и Roles значениями по умолчанию.
     */
    private void initializeDefaultRolesAndRights() {
        // Добавление ролей по умолчанию
        Roles adminRole = new Roles(1L, "ADMIN");
        Roles userRole = new Roles(USER_ROLE_ID, "USER");
        rolesRepository.put(adminRole.getId(), adminRole);
        rolesRepository.put(userRole.getId(), userRole);

        // Добавление прав по умолчанию
        Rights writeRight = new Rights(1L, "WRITE");
        Rights editRight = new Rights(2L, "EDIT");
        Rights deleteRight = new Rights(3L, "DELETE");
        Rights statisticsRight = new Rights(4L, "STATISTICS");
        rightsRepository.put(writeRight.getId(), writeRight);
        rightsRepository.put(editRight.getId(), editRight);
        rightsRepository.put(deleteRight.getId(), deleteRight);
        rightsRepository.put(statisticsRight.getId(), statisticsRight);
    }
}

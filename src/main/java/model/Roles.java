package model;

/**
 * Перечисление, представляющее роли пользователей.
 */
public enum Roles {
    /**
     * Роль обычного пользователя.
     */
    USER,

    /**
     * Роль администратора.
     */
    ADMIN;

    /**
     * Переопределение метода {@code toString()} для получения текстового представления роли.
     *
     * @return Текстовое представление роли
     */
    @Override
    public String toString() {
        return switch (this) {
            case USER -> "USER";
            case ADMIN -> "ADMIN";
        };
    }
}


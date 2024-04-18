package model;

/**
 * Перечисление, представляющее различные права доступа пользователя.
 */
public enum Rights {
    /**
     * Право на запись (добавление данных).
     */
    WRITE,

    /**
     * Право на редактирование данных.
     */
    EDIT,

    /**
     * Право на удаление данных.
     */
    DELETE,

    /**
     * Право на доступ к статистике.
     */
    STATISTICS;

    /**
     * Переопределение метода {@code toString()} для получения текстового представления права.
     *
     * @return Текстовое представление права
     */
    @Override
    public String toString() {
        return switch (this) {
            case WRITE -> "WRITE";
            case EDIT -> "EDIT";
            case DELETE -> "DELETE";
            case STATISTICS -> "STATISTICS";
        };
    }
}


package out.menu.statistic;

/**
 * Интерфейс TriFunction представляет функцию, принимающую три аргумента и возвращающую результат.
 *
 * @param <T> Тип первого аргумента.
 * @param <U> Тип второго аргумента.
 * @param <V> Тип третьего аргумента.
 * @param <R> Тип результата.
 */
@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    /**
     * Принимает три аргумента и возвращает результат.
     *
     * @param t Первый аргумент.
     * @param u Второй аргумент.
     * @param v Третий аргумент.
     * @return Результат функции.
     */
    R apply(T t, U u, V v);
}

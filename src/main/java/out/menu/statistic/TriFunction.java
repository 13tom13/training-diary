package out.menu.statistic;

import in.exception.RepositoryException;

@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}

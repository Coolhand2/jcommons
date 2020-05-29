package org.example.commons.api;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

public interface TypedRepository<T, S> {
    List<T> findAll();
    List<T> findAll(Comparator<T> comparator);
    List<T> findAll(Predicate<T> predicate);
    List<T> findAll(Comparator<T> comparator, Predicate<T> predicate);
    T findById(S id);
    List<T> findByIds(S... ids);
    List<T> findByIds(Iterable<S> ids);
    <X> List<T> findByColumn(SingularAttribute<T, X> column, X... values);
    <X> List<T> findByColumn(SingularAttribute<T, X> column, Iterable<X> values);
    <X> List<T> findByColumns(Map<SingularAttribute<T, X>, Iterable<X>> columns);
    void create(T... entities);
    void create(Iterable<T> entities);
    void update(T... entities);
    void update(Iterable<T> entities);
    void delete(T... entities);
    void delete(Iterable<T> entities);
}

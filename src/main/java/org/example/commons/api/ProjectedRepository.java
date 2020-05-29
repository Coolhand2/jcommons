package org.example.commons.api;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import javax.persistence.metamodel.SingularAttribute;

public interface ProjectedRepository<T, S, X> {
    List<X> findAllProjected();
    List<X> findAllProjected(Comparator<X> comparator);
    List<X> findAllProjected(Predicate<X> predicate);
    List<X> findAllProjected(Comparator<X> comparator, Predicate<X> predicate);
    X findByIdProjected(S id);
    List<X> findByIdsProjected(S... ids);
    List<X> findByIdsProjected(Iterable<S> ids);
    <Y> List<X> findByColumnProjected(SingularAttribute<T, Y> column, Y... values);
    <Y> List<X> findByColumnProjected(SingularAttribute<T, Y> column, Iterable<Y> values);
    <Y> List<X> findByColumnsProjected(Map<SingularAttribute<T, Y>, Iterable<Y>> columns);
}

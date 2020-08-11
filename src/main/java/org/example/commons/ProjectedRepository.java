package org.example.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import static java.util.stream.Collectors.toList;

public interface ProjectedRepository<T, S, X> extends TypedRepository<T, S> {

    List<SingularAttribute<T, ?>> getProjectionList();

    Class<X> getProjectedClass();

    default List<X> findAllProjected() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        return getSession().createQuery(query).getResultList();
    }

    default X findByIdProjected(S id) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        query.where(builder.equal(root.get(getId()), id));
        return getSession().createQuery(query).getSingleResult();
    }

    default List<X> findByIdsProjected(S... ids) {
        return findByIdsProjected(List.of(ids));
    }

    default List<X> findByIdsProjected(Collection<S> ids) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        query.where(builder.and(Stream.of(ids).map(e -> root.get(getId()).in(e)).toArray(Predicate[]::new)));
        return getSession().createQuery(query).getResultList();
    }

    default <Y> X findOneByColumnProjected(SingularAttribute<T, Y> column, Y value) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        query.where(builder.equal(root.get(column), value));
        return getSession().createQuery(query).getSingleResult();
    }
    
    default <Y> List<X> findByColumnProjected(SingularAttribute<T, Y> column, Y... values) {
        return findByColumnProjected(column, List.of(values));
    }

    default <Y> List<X> findByColumnProjected(SingularAttribute<T, Y> column, Iterable<Y> values) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        query.where(root.get(column).in(values));
        return getSession().createQuery(query).getResultList();
    }

    default X findOneByColumnsProjected(Map<SingularAttribute<T, ?>, Iterable<?>> columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        query.where(builder.and(columns.entrySet().stream().map(e -> root.get(e.getKey()).in(e.getValue())).toArray(Predicate[]::new)));
        return getSession().createQuery(query).getSingleResult();
    }

    default List<X> findByColumnsProjected(Map<SingularAttribute<T, ?>, Iterable<?>> columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        query.where(builder.and(columns.entrySet().stream().map(e -> root.get(e.getKey()).in(e.getValue())).toArray(Predicate[]::new)));
        return getSession().createQuery(query).getResultList();
    }
    
    default X findOneByProjected(BiFunction<Root<T>, CriteriaBuilder, Predicate[]> by) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        query.where(builder.and(by.apply(root, builder)));
        return getSession().createQuery(query).getSingleResult();
    }
    
    default List<X> findByProjected(BiFunction<Root<T>, CriteriaBuilder, Predicate[]> by) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        query.where(builder.and(by.apply(root, builder)));
        return getSession().createQuery(query).getResultList();
    }
}

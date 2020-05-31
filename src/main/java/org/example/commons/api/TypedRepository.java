package org.example.commons.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.SingularAttribute;

public interface TypedRepository<T, S> extends BasicRepository<T>{

    default List<T> findAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        return getSession().createQuery(query).getResultList();
    }

    default List<T> findAll(Comparator<T> comparator) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        return getSession().createQuery(query).getResultStream().parallel()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    default List<T> findAll(Predicate<T> predicate) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        return getSession().createQuery(query).getResultStream().parallel()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    default List<T> findAll(Comparator<T> comparator, Predicate<T> predicate) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        return getSession().createQuery(query).getResultStream().parallel()
                .filter(predicate)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    default T findById(S id) {
        return getSession().find(getEntityClass(), id);
    }

    default List<T> findByIds(S... ids) {
        return findByIds(List.of(ids));
    }

    default List<T> findByIds(Iterable<S> ids) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        List<javax.persistence.criteria.Predicate> ins = new ArrayList<>();
        ids.forEach((id) -> ins.add(builder.equal(root.get("id"), id)));
        query.select(root).where(builder.or(ins.toArray(new javax.persistence.criteria.Predicate[0])));
        return getSession().createQuery(query).getResultList();
    }

    default <X> T findOneByColumn(SingularAttribute<T, X> column, X value) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root).where(builder.equal(root.get(column), value));
        return getSession().createQuery(query).getSingleResult();
    }

    default <X> List<T> findByColumn(SingularAttribute<T, X> column, X... values) {
        return findByColumn(column, Arrays.asList(values));
    }

    default <X> List<T> findByColumn(SingularAttribute<T, X> column, Iterable<X> values) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root).where(root.get(column).in(values));
        return getSession().createQuery(query).getResultList();
    }

    default T findOneByColumns(Map<SingularAttribute<T, ?>, Iterable<?>> columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        columns.forEach((key, value) ->
                query.where(root.get(key).in(value))
        );
        return getSession().createQuery(query).getSingleResult();
    }
    
    default List<T> findByColumns(Map<SingularAttribute<T, ?>, Iterable<?>> columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        columns.forEach((key, value) ->
                query.where(root.get(key).in(value))
        );
        return getSession().createQuery(query).getResultList();
    }

    default void create(T... entities) {
        create(List.of(entities));
    }

    default void create(Iterable<T> entities) {
        getEntityManager().getTransaction().begin();
        entities.forEach(getEntityManager()::persist);
        getEntityManager().getTransaction().commit();
    }

    default void update(T... entities) {
        update(List.of(entities));
    }

    default void update(Iterable<T> entities) {
        getEntityManager().getTransaction().begin();
        entities.forEach(getEntityManager()::merge);
        getEntityManager().getTransaction().commit();
    }

    default void delete(T... entities) {
        delete(List.of(entities));
    }

    default void delete(Iterable<T> entities) {
        getSession().getTransaction().begin();
        entities.forEach(e -> getSession().delete(e));
        getSession().getTransaction().commit();
    }
}

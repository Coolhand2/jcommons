package org.example.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.slf4j.Logger;

public interface TypedRepository<T, S> extends BasicRepository<T> {

    SingularAttribute<T, S> getId();

    Logger getLogger();

    default List<T> findAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        return getSession().createQuery(query).getResultList();
    }

    default T findById(S id) {
        return getSession().find(getEntityClass(), id);
    }

    default List<T> findByIds(S... ids) {
        return findByIds(List.of(ids));
    }

    default List<T> findByIds(Collection<S> ids) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root).where(root.get(getId()).in(ids));
        return getSession().createQuery(query).getResultList();
    }

    default <X> T findOneByColumn(Function<Root<T>, Expression<X>> column, X value) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root).where(builder.equal(column.apply(root), value));
        return getSession().createQuery(query).getSingleResult();
    }

    default <X> T findOneByColumn(SingularAttribute<T, X> column, X value) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root).where(builder.equal(root.get(column), value));
        return getSession().createQuery(query).getSingleResult();
    }

    default <X> List<T> findByColumn(Function<Root<T>, Expression<X>> column, X value) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root).where(builder.equal(column.apply(root), value));
        return getSession().createQuery(query).getResultList();
    }

    default <X> List<T> findByColumn(SingularAttribute<T, X> column, X... values) {
        return findByColumn(column, List.of(values));
    }

    default <X> List<T> findByColumn(SingularAttribute<T, X> column, Iterable<X> values) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root).where(root.get(column).in(values));
        return getSession().createQuery(query).getResultList();
    }

    default <X> T findOneByColumns(Map.Entry<Function<Root<T>, Expression<X>>, X>... columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        Predicate[] predicates = Arrays.stream(columns)
                .map(e -> e.getKey().apply(root).in(e.getValue()))
                .toArray(Predicate[]::new);
        query.select(root).where(builder.and(predicates));
        return getSession().createQuery(query).getSingleResult();
    }

    default <X> T findOneByColumns(Map<Function<Root<T>, Expression<X>>, X> columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        Predicate[] predicates = columns.entrySet().stream()
                .map(e -> e.getKey().apply(root).in(e.getValue()))
                .toArray(Predicate[]::new);
        query.select(root).where(builder.and(predicates));
        return getSession().createQuery(query).getSingleResult();
    }

    default <X> List<T> findByColumns(Map.Entry<Function<Root<T>, Expression<X>>, Iterable<X>>... columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        Predicate[] predicates = Arrays.stream(columns)
                .map(e -> e.getKey().apply(root).in(e.getValue()))
                .toArray(Predicate[]::new);
        query.select(root).where(builder.and(predicates));
        return getSession().createQuery(query).getResultList();
    }

    default <X> List<T> findByColumns(Map<Function<Root<T>, Expression<X>>, Iterable<X>> columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        Predicate[] predicates = columns.entrySet().stream()
                .map(e -> e.getKey().apply(root).in(e.getValue()))
                .toArray(Predicate[]::new);
        query.select(root).where(builder.and(predicates));
        return getSession().createQuery(query).getResultList();
    }

    default T findOneBy(BiFunction<Root<T>, CriteriaBuilder, Predicate[]> by) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root).where(builder.and(by.apply(root, builder)));
        return getSession().createQuery(query).getSingleResult();
    }

    default List<T> findBy(BiFunction<Root<T>, CriteriaBuilder, Predicate[]> by) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root).where(builder.and(by.apply(root, builder)));
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

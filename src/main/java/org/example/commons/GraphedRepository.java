package org.example.commons;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.SingularAttribute;

public interface GraphedRepository<T, S> extends BasicRepository<T> {

    default List<T> findAllGraphed(Function<Root<T>, List<Selection<?>>> graph) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.apply(root));
        return getSession().createQuery(query).getResultList();
    }

    default T findByIdGraphed(Function<Root<T>, List<Selection<?>>> graph, S id) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.apply(root)).where(builder.equal(root.<S>get("id"), id));
        return getSession().createQuery(query).getSingleResult();
    }

    default List<T> findByIdsGraphed(Function<Root<T>, List<Selection<?>>> graph, S... ids) {
        return findByIdsGraphed(graph, Arrays.asList(ids));
    }

    default List<T> findByIdsGraphed(Function<Root<T>, List<Selection<?>>> graph, List<S> ids) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.apply(root)).where(root.<S>get("id").in(ids));
        return getSession().createQuery(query).getResultList();
    }

    default <X> T findOneByColumnGraphed(Function<Root<T>, List<Selection<?>>> graph, SingularAttribute<T, X> column, X value) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.apply(root)).where(builder.equal(root.get(column), value));
        return getSession().createQuery(query).getSingleResult();
    }

    default <X> List<T> findByColumnGraphed(Function<Root<T>, List<Selection<?>>> graph, SingularAttribute<T, X> column, X... values) {
        return findByColumnGraphed(graph, column, Arrays.asList(values));
    }

    default <X> List<T> findByColumnGraphed(Function<Root<T>, List<Selection<?>>> graph, SingularAttribute<T, X> column, Iterable<X> values) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.apply(root)).where(root.get(column).in(values));
        return getSession().createQuery(query).getResultList();
    }

    default T findOneByColumnsGraphed(Function<Root<T>, List<Selection<?>>> graph, Map<SingularAttribute<T, ?>, Iterable<?>> columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.apply(root));
        query.where(builder.and(columns.entrySet().stream().map(e -> root.get(e.getKey()).in(e.getValue())).toArray(Predicate[]::new)));
        return getSession().createQuery(query).getSingleResult();
    }

    default List<T> findByColumnsGraphed(Function<Root<T>, List<Selection<?>>> graph, Map<SingularAttribute<T, ?>, Iterable<?>> columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.apply(root));
        query.where(builder.and(columns.entrySet().stream().map(e -> root.get(e.getKey()).in(e.getValue())).toArray(Predicate[]::new)));
        return getSession().createQuery(query).getResultList();
    }

    default T findOneByGraphed(Function<Root<T>, List<Selection<?>>> graph, BiFunction<Root<T>, CriteriaBuilder, Predicate[]> by) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.apply(root)).where(builder.and(by.apply(root, builder)));
        return getSession().createQuery(query).getSingleResult();
    }

    default List<T> findByGraphed(Function<Root<T>, List<Selection<?>>> graph, BiFunction<Root<T>, CriteriaBuilder, Predicate[]> by) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.apply(root)).where(builder.and(by.apply(root, builder)));
        return getSession().createQuery(query).getResultList();
    }
}

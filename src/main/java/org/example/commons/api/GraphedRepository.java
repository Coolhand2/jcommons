package org.example.commons.api;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface GraphedRepository<T, S> extends BasicRepository<T> {

    default List<T> findAllGraphed(List<SingularAttribute<T, ?>> graph) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.stream().map(root::get).collect(Collectors.toList()));
        return getSession().createQuery(query).getResultList();
    }

    default List<T> findAllGraphed(List<SingularAttribute<T, ?>> graph, Comparator<T> comparator) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.stream().map(root::get).collect(Collectors.toList()));
        return getSession().createQuery(query).getResultStream().sorted(comparator).collect(Collectors.toList());
    }

    default List<T> findAllGraphed(List<SingularAttribute<T, ?>> graph, Predicate<T> predicate) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.stream().map(root::get).collect(Collectors.toList()));
        return getSession().createQuery(query).getResultStream().filter(predicate).collect(Collectors.toList());
    }

    default List<T> findAllGraphed(List<SingularAttribute<T, ?>> graph, Comparator<T> comparator, Predicate<T> predicate) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.stream().map(root::get).collect(Collectors.toList()));
        return getSession().createQuery(query).getResultStream().filter(predicate).sorted(comparator).collect(Collectors.toList());
    }

    default T findByIdGraphed(List<SingularAttribute<T, ?>> graph, S id) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.stream().map(root::get).collect(Collectors.toList())).where(builder.equal(root.<S>get("id"), id));
        return getSession().createQuery(query).getSingleResult();
    }

    default List<T> findByIdsGraphed(List<SingularAttribute<T, ?>> graph, S... ids) {
        return findByIdsGraphed(graph, Arrays.asList(ids));
    }

    default List<T> findByIdsGraphed(List<SingularAttribute<T, ?>> graph, List<S> ids) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.stream().map(root::get).collect(Collectors.toList())).where(root.<S>get("id").in(ids));
        return getSession().createQuery(query).getResultList();
    }

    default <X> T findOneByColumnGraphed(List<SingularAttribute<T, ?>> graph, SingularAttribute<T, X> column, X value) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.stream().map(root::get).collect(Collectors.toList())).where(builder.equal(root.get(column), value));
        return getSession().createQuery(query).getSingleResult();
    }

    default <X> List<T> findByColumnGraphed(List<SingularAttribute<T, ?>> graph, SingularAttribute<T, X> column, X... values) {
        return findByColumnGraphed(graph, column, Arrays.asList(values));
    }

    default <X> List<T> findByColumnGraphed(List<SingularAttribute<T, ?>> graph, SingularAttribute<T, X> column, Iterable<X> values) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.stream().map(root::get).collect(Collectors.toList())).where(root.get(column).in(values));
        return getSession().createQuery(query).getResultList();
    }

    default T findOneByColumnsGraphed(List<SingularAttribute<T, ?>> graph, Map<SingularAttribute<T, ?>, Iterable<?>> columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.stream().map(root::get).collect(Collectors.toList()));
        List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
        columns.forEach((key, value) -> {
            predicates.add(root.get(key).in(value));
        });
        if (predicates.size() == 1) {
            query.where(predicates.get(0));
        } else {
            query.where(builder.and(
                    predicates.toArray(new javax.persistence.criteria.Predicate[0])
            ));
        }
        return getSession().createQuery(query).getSingleResult();
    }

    default List<T> findByColumnsGraphed(List<SingularAttribute<T, ?>> graph, Map<SingularAttribute<T, ?>, Iterable<?>> columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(graph.stream().map(root::get).collect(Collectors.toList()));
        List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
        columns.forEach((key, value) -> {
            predicates.add(root.get(key).in(value));
        });
        if (predicates.size() == 1) {
            query.where(predicates.get(0));
        } else {
            query.where(builder.and(
                    predicates.toArray(new javax.persistence.criteria.Predicate[0])
            ));
        }
        return getSession().createQuery(query).getResultList();
    }
}

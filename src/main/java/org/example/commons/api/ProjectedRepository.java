package org.example.commons.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import static java.util.stream.Collectors.toList;

public interface ProjectedRepository<T, S, X> extends BasicRepository<T> {

    List<SingularAttribute<T, ?>> getProjectionList();
    Class<X> getProjectedClass();

    default List<X> findAllProjected() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        return getSession().createQuery(query).getResultList();
    }

    default List<X> findAllProjected(Comparator<X> comparator) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        return getSession().createQuery(query).getResultStream().sorted(comparator).collect(toList());
    }

    default List<X> findAllProjected(Predicate<X> predicate) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        return getSession().createQuery(query).getResultStream().filter(predicate).collect(toList());
    }

    default List<X> findAllProjected(Comparator<X> comparator, Predicate<X> predicate) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        return getSession().createQuery(query).getResultStream().filter(predicate).sorted(comparator).collect(toList());
    }

    default X findByIdProjected(S id) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        query.where(builder.equal(root.get("id"), id));
        return getSession().createQuery(query).getSingleResult();
    }

    default List<X> findByIdsProjected(S... ids) {
        return findByIdsProjected(List.of(ids));
    }

    default List<X> findByIdsProjected(Iterable<S> ids) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        List<javax.persistence.criteria.Predicate> ins = new ArrayList<>();
        ids.forEach((id) -> ins.add(builder.equal(root.get("id"), id)));
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        query.where(builder.or(ins.toArray(new javax.persistence.criteria.Predicate[0])));
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
        columns.forEach((key, value) -> query.where(root.get(key).in(value)));
        return getSession().createQuery(query).getSingleResult();
    }

    default <Y> List<X> findByColumnsProjected(Map<SingularAttribute<T, Y>, Iterable<Y>> columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(getProjectedClass());
        Root<T> root = query.from(getEntityClass());
        query.multiselect(getProjectionList().stream().map(root::get).collect(toList()));
        columns.forEach((key, value) -> query.where(root.get(key).in(value)));
        return getSession().createQuery(query).getResultList();
    }
}

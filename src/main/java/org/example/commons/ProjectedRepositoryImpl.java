package org.example.commons;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.example.commons.api.ProjectedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectedRepositoryImpl<T, S, X> extends TypedRepositoryImpl<T, S> implements ProjectedRepository<T, S, X> {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectedRepositoryImpl.class);

    protected final List<SingularAttribute<T, ?>> projection;
    protected final Class<X> projectedClass;

    public ProjectedRepositoryImpl(Class<T> cls, List<SingularAttribute<T, ?>> projectionList, Class<X> projectedClassName) {
        super(cls);
        projection = projectionList;
        projectedClass = projectedClassName;
    }

    @Override
    public List<X> findAllProjected() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(projectedClass);
        Root<T> root = query.from(entityClass);
        query.multiselect(projection.stream().map(root::get).collect(Collectors.toList()));
        return getSession().createQuery(query).getResultList();
    }

    @Override
    public List<X> findAllProjected(Comparator<X> comparator) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(projectedClass);
        Root<T> root = query.from(entityClass);
        query.multiselect(projection.stream().map(root::get).collect(Collectors.toList()));
        return getSession().createQuery(query).getResultStream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public List<X> findAllProjected(Predicate<X> predicate) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(projectedClass);
        Root<T> root = query.from(entityClass);
        query.multiselect(projection.stream().map(root::get).collect(Collectors.toList()));
        return getSession().createQuery(query).getResultStream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public List<X> findAllProjected(Comparator<X> comparator, Predicate<X> predicate) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(projectedClass);
        Root<T> root = query.from(entityClass);
        query.multiselect(projection.stream().map(root::get).collect(Collectors.toList()));
        return getSession().createQuery(query).getResultStream().filter(predicate).sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public X findByIdProjected(S id) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(projectedClass);
        Root<T> root = query.from(entityClass);
        query.multiselect(projection.stream().map(root::get).collect(Collectors.toList()));
        query.where(builder.equal(root.get("id"), id));
        return getSession().createQuery(query).getSingleResult();
    }

    @SafeVarargs
    @Override
    public final List<X> findByIdsProjected(S... ids) {
        return findByIdsProjected(List.of(ids));
    }

    @Override
    public List<X> findByIdsProjected(Iterable<S> ids) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(projectedClass);
        Root<T> root = query.from(entityClass);
        query.multiselect(projection.stream().map(root::get).collect(Collectors.toList()));
        query.where(root.get("id").in(ids));
        return getSession().createQuery(query).getResultList();
    }

    @SafeVarargs
    @Override
    public final <Y> List<X> findByColumnProjected(SingularAttribute<T, Y> column, Y... values) {
        return findByColumnProjected(column, List.of(values));
    }

    @Override
    public <Y> List<X> findByColumnProjected(SingularAttribute<T, Y> column, Iterable<Y> values) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(projectedClass);
        Root<T> root = query.from(entityClass);
        query.multiselect(projection.stream().map(root::get).collect(Collectors.toList()));
        query.where(root.get(column).in(values));
        return getSession().createQuery(query).getResultList();
    }

    @Override
    public <Y> List<X> findByColumnsProjected(Map<SingularAttribute<T, Y>, Iterable<Y>> columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<X> query = builder.createQuery(projectedClass);
        Root<T> root = query.from(entityClass);
        query.multiselect(projection.stream().map(root::get).collect(Collectors.toList()));
        columns.forEach((key, value) -> query.where(root.get(key).in(value)));
        return getSession().createQuery(query).getResultList();
    }
}

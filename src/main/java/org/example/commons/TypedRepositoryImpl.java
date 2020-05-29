package org.example.commons;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.example.commons.api.TypedRepository;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypedRepositoryImpl<T, S> implements TypedRepository<T, S> {

    private static final Logger LOG = LoggerFactory.getLogger(TypedRepositoryImpl.class);

    @Inject
    private EntityManager em;

    protected final Class<T> entityClass;

    protected TypedRepositoryImpl(Class<T> cls) {
        entityClass = cls;
    }

    protected Session getSession() {
        return em.unwrap(Session.class);
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root);
        return getSession().createQuery(query).getResultList();
    }

    @Override
    public List<T> findAll(Comparator<T> comparator) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root);
        return getSession().createQuery(query).getResultStream().parallel().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public List<T> findAll(Predicate<T> predicate) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root);
        return getSession().createQuery(query).getResultStream().parallel().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public List<T> findAll(Comparator<T> comparator, Predicate<T> predicate) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root);
        return getSession().createQuery(query).getResultStream().parallel().filter(predicate).sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public T findById(S id) {
        return getSession().find(entityClass, id);
    }

    @SafeVarargs
    @Override
    public final List<T> findByIds(S... ids) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root).where(root.get("id").in(Arrays.asList(ids)));
        return getSession().createQuery(query).getResultList();
    }

    @Override
    public List<T> findByIds(Iterable<S> ids) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root).where(root.get("id").in(ids));
        return getSession().createQuery(query).getResultList();
    }

    @SafeVarargs
    @Override
    public final <X> List<T> findByColumn(SingularAttribute<T, X> column, X... values) {
        return findByColumn(column, Arrays.asList(values));
    }

    @Override
    public <X> List<T> findByColumn(SingularAttribute<T, X> column, Iterable<X> values) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root).where(root.get(column).in(values));
        return getSession().createQuery(query).getResultList();
    }

    @Override
    public <X> List<T> findByColumns(Map<SingularAttribute<T, X>, Iterable<X>> columns) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        columns.forEach((key, value) ->
                query.select(root).where(root.get(key).in(value))
        );
        return getSession().createQuery(query).getResultList();
    }

    @SafeVarargs
    @Override
    public final void create(T... entities) {
        create(List.of(entities));
    }

    @Override
    public void create(Iterable<T> entities) {
        em.getTransaction().begin();
        entities.forEach(em::persist);
        em.getTransaction().commit();
    }

    @SafeVarargs
    @Override
    public final void update(T... entities) {
        update(List.of(entities));
    }

    @Override
    public void update(Iterable<T> entities) {
        em.getTransaction().begin();
        entities.forEach(em::merge);
        em.getTransaction().commit();
    }

    @SafeVarargs
    @Override
    public final void delete(T... entities) {
        delete(List.of(entities));
    }

    @Override
    public void delete(Iterable<T> entities) {
        getSession().getTransaction().begin();
        entities.forEach(e -> getSession().delete(e));
        getSession().getTransaction().commit();
    }
}

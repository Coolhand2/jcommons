package org.example.commons.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.example.commons.api.TypedFilter;
import org.example.commons.entities.Group;
import org.example.commons.entities.Group_;
import org.example.commons.entities.filters.GroupFilter;
import org.example.commons.repositories.api.GroupRepository;

public class GroupRepositoryImpl implements GroupRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public List<Group> filter(TypedFilter<Group> f) {
        GroupFilter filter = (GroupFilter) f;
        if(GroupFilter.DEFAULT.equals(filter)) {
            return findAll();
        }
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Group> query = builder.createQuery(Group.class);
        Root<Group> root = query.from(Group.class);
        List<Predicate> predicates = new ArrayList<>();
        if(!filter.getId().isBlank()) {
            predicates.add(builder.like(root.get(Group_.id).as(String.class), '%' + filter.getId() + '%'));
        }
        if(!filter.getName().isBlank()) {
            predicates.add(builder.like(builder.lower(root.get(Group_.name)), '%' + filter.getName().toLowerCase(Locale.getDefault()) + '%'));
        }
        if(!filter.getDescription().isBlank()) {
            predicates.add(builder.like(builder.lower(root.get(Group_.description)), '%' + filter.getDescription().toLowerCase(Locale.getDefault()) + '%'));
        }
        query.select(root);
        if(predicates.size() == 1) {
            query.where(predicates.get(0));
        } else {
            query.where(builder.and(predicates.toArray(new Predicate[0])));
        }
        return getSession().createQuery(query).getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Class<Group> getEntityClass() {
        return Group.class;
    }
}

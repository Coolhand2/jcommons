package org.example.base.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.example.base.entities.Group_;
import org.example.base.entities.filters.GroupFilter;
import org.example.commons.FilterableRepository;
import org.example.commons.TypedFilter;
import org.example.commons.TypedRepository;
import org.example.base.entities.Group;

public interface GroupRepository extends TypedRepository<Group, Long>, FilterableRepository<Group> {

    @Override
    default Class<Group> getEntityClass() {
        return Group.class;
    }

    @Override
    default SingularAttribute<Group, Long> getId() {
        return Group_.id;
    }

    @Override
    default List<Group> filter(TypedFilter<Group> f) {
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
}

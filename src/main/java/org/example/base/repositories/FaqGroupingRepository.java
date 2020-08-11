package org.example.base.repositories;

import javax.persistence.metamodel.SingularAttribute;
import org.example.base.entities.FaqGrouping_;
import org.example.commons.TypedRepository;
import org.example.base.entities.FaqGrouping;

public interface FaqGroupingRepository extends TypedRepository<FaqGrouping, Long> {

    @Override
    default Class<FaqGrouping> getEntityClass() {
        return FaqGrouping.class;
    }

    @Override
    default SingularAttribute<FaqGrouping, Long> getId() {
        return FaqGrouping_.id;
    }
}

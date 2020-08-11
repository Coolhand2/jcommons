package org.example.base.repositories;

import javax.persistence.metamodel.SingularAttribute;
import org.example.base.entities.HelpGrouping_;
import org.example.commons.TypedRepository;
import org.example.base.entities.HelpGrouping;

public interface HelpGroupingRepository extends TypedRepository<HelpGrouping, Long> {

    @Override
    default Class<HelpGrouping> getEntityClass() {
        return HelpGrouping.class;
    }

    @Override
    default SingularAttribute<HelpGrouping, Long> getId() {
        return HelpGrouping_.id;
    }
}

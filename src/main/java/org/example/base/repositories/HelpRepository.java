package org.example.base.repositories;

import javax.persistence.metamodel.SingularAttribute;
import org.example.base.entities.Help_;
import org.example.commons.TypedRepository;
import org.example.base.entities.Help;

public interface HelpRepository extends TypedRepository<Help, Long> {
    @Override
    default Class<Help> getEntityClass() {
        return Help.class;
    }

    @Override
    default SingularAttribute<Help, Long> getId() {
        return Help_.id;
    }
}

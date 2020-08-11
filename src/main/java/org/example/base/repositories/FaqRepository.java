package org.example.base.repositories;

import javax.persistence.metamodel.SingularAttribute;
import org.example.base.entities.Faq_;
import org.example.commons.TypedRepository;
import org.example.base.entities.Faq;

public interface FaqRepository extends TypedRepository<Faq, Long> {

    @Override
    default Class<Faq> getEntityClass() {
        return Faq.class;
    }

    @Override
    default SingularAttribute<Faq, Long> getId() {
        return Faq_.id;
    }

}

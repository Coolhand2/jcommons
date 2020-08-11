package org.example.base.repositories;

import javax.persistence.metamodel.SingularAttribute;
import org.example.base.entities.PreferenceValue_;
import org.example.commons.TypedRepository;
import org.example.base.entities.PreferenceValue;

public interface PreferenceValueRepository extends TypedRepository<PreferenceValue, Long> {

    @Override
    default Class<PreferenceValue> getEntityClass() {
        return PreferenceValue.class;
    }

    @Override
    default SingularAttribute<PreferenceValue, Long> getId() {
        return PreferenceValue_.id;
    }

}

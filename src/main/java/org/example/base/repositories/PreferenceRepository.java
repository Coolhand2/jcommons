package org.example.base.repositories;

import javax.persistence.metamodel.SingularAttribute;
import org.example.base.entities.Preference_;
import org.example.commons.TypedRepository;
import org.example.base.entities.Preference;

public interface PreferenceRepository extends TypedRepository<Preference, Long> {

    @Override
    default Class<Preference> getEntityClass() {
        return Preference.class;
    }

    @Override
    default SingularAttribute<Preference, Long> getId() {
        return Preference_.id;
    }

}

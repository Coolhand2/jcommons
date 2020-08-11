package org.example.base.repositories;

import javax.persistence.metamodel.SingularAttribute;
import org.example.commons.TypedRepository;
import org.example.base.entities.Configuration;
import org.example.base.entities.Configuration_;

public interface ConfigurationRepository extends TypedRepository<Configuration, Long> {

    @Override
    default Class<Configuration> getEntityClass() {
        return Configuration.class;
    }

    @Override
    default SingularAttribute<Configuration, Long> getId() {
        return Configuration_.id;
    }

    default Configuration findByName(String name) {
        return findOneByColumn(Configuration_.name, name);
    }
}

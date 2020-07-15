package org.example.commons.repositories.api;

import org.example.commons.api.FilteredRepository;
import org.example.commons.api.TypedRepository;
import org.example.commons.entities.Configuration;
import org.example.commons.entities.Configuration_;

public interface ConfigurationRepository extends TypedRepository<Configuration, Long>, FilteredRepository<Configuration> {
    default Configuration findByName(String name) {
        return findOneByColumn(Configuration_.name, name);
    }
}

package org.example.commons.repositories;

import org.example.commons.api.TypedFilter;
import org.example.commons.entities.Configuration;
import org.example.commons.repositories.api.ConfigurationRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationRepositoryImpl implements ConfigurationRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Class<Configuration> getEntityClass() {
        return Configuration.class;
    }

    @Override
    public List<Configuration> filter(TypedFilter<Configuration> filter) {
        return new ArrayList<>();
    }
}

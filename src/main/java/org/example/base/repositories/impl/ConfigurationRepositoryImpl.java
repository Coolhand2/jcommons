package org.example.base.repositories.impl;

import javax.ejb.Singleton;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.example.base.repositories.ConfigurationRepository;
import org.slf4j.Logger;

@Singleton
public class ConfigurationRepositoryImpl implements ConfigurationRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Inject
    private Logger logger;

    @Override
    public Logger getLogger() {
        return logger;
    }
}

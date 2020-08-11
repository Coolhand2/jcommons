package org.example.base.repositories.impl;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.SingularAttribute;
import org.example.base.entities.PreferenceValue;
import org.example.base.entities.PreferenceValue_;
import org.example.base.repositories.PreferenceValueRepository;
import org.slf4j.Logger;

@Singleton
public class PreferenceValueRepositoryImpl implements PreferenceValueRepository {

    @Inject
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    @Inject
    private Logger logger;

    @Override
    public Logger getLogger() {
        return logger;
    }
}

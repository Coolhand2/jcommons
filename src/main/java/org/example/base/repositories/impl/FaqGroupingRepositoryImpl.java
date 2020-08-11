package org.example.base.repositories.impl;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.example.base.repositories.FaqGroupingRepository;
import org.slf4j.Logger;

@Singleton
public class FaqGroupingRepositoryImpl implements FaqGroupingRepository {

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
        return null;
    }
}

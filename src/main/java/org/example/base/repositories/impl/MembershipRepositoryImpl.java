package org.example.base.repositories.impl;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.example.base.repositories.MembershipRepository;
import org.slf4j.Logger;

@Singleton
public class MembershipRepositoryImpl implements MembershipRepository {

    @Inject
    private Logger logger;

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}

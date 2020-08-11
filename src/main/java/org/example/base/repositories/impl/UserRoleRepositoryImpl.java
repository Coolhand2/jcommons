package org.example.base.repositories.impl;

import javax.ejb.Singleton;
import javax.persistence.metamodel.SingularAttribute;
import org.example.base.entities.UserRole;
import org.example.base.entities.UserRole_;
import org.example.base.repositories.UserRoleRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.slf4j.Logger;

@Singleton
public class UserRoleRepositoryImpl implements UserRoleRepository {

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

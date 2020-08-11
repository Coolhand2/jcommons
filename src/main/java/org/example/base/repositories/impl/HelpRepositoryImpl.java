package org.example.base.repositories.impl;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.SingularAttribute;
import org.example.base.entities.Help;
import org.example.base.entities.Help_;
import org.example.base.repositories.HelpRepository;
import org.slf4j.Logger;

@Singleton
public class HelpRepositoryImpl implements HelpRepository {

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

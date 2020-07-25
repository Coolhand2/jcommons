package org.example.commons.repositories;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.example.commons.entities.Help;
import org.example.commons.repositories.api.HelpRepository;

public class HelpRepositoryImpl implements HelpRepository {

    @Inject
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<Help> getEntityClass() {
        return Help.class;
    }
}

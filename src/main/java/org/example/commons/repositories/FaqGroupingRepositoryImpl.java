package org.example.commons.repositories;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.example.commons.entities.FaqGrouping;
import org.example.commons.repositories.api.FaqGroupingRepository;

public class FaqGroupingRepositoryImpl implements FaqGroupingRepository {

    @Inject
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<FaqGrouping> getEntityClass() {
        return FaqGrouping.class;
    }
}

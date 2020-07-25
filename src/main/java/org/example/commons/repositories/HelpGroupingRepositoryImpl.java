package org.example.commons.repositories;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.example.commons.entities.HelpGrouping;
import org.example.commons.repositories.api.HelpGroupingRepository;

public class HelpGroupingRepositoryImpl implements HelpGroupingRepository {

    @Inject
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<HelpGrouping> getEntityClass() {
        return HelpGrouping.class;
    }
}

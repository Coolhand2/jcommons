package org.example.commons.repositories;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.example.commons.entities.Faq;
import org.example.commons.repositories.api.FaqRepository;

public class FaqRepositoryImpl implements FaqRepository {

    @Inject
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<Faq> getEntityClass() {
        return Faq.class;
    }
}

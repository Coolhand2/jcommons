package org.example.commons.repositories;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.example.commons.entities.PreferenceValue;
import org.example.commons.repositories.api.PreferenceValueRepository;

public class PreferenceValueRepositoryImpl implements PreferenceValueRepository {

    @Inject
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<PreferenceValue> getEntityClass() {
        return PreferenceValue.class;
    }
}

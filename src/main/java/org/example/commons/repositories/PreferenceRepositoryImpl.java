package org.example.commons.repositories;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.example.commons.entities.Preference;
import org.example.commons.repositories.api.PreferenceRepository;

public class PreferenceRepositoryImpl implements PreferenceRepository {

    @Inject
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<Preference> getEntityClass() {
        return Preference.class;
    }
}

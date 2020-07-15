package org.example.commons.repositories;

import org.example.commons.entities.UserRole;
import org.example.commons.repositories.api.UserRoleRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class UserRoleRepositoryImpl implements UserRoleRepository {

    @Inject
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<UserRole> getEntityClass() {
        return UserRole.class;
    }
}

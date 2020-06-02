package org.example.commons.repositories;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.example.commons.entities.Membership;
import org.example.commons.repositories.api.MembershipRepository;

public class MembershipRepositoryImpl implements MembershipRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Class<Membership> getEntityClass() {
        return Membership.class;
    }
}

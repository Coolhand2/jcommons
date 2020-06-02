package org.example.commons.repositories;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.example.commons.api.TypedFilter;
import org.example.commons.entities.Group;
import org.example.commons.repositories.api.GroupRepository;

public class GroupRepositoryImpl implements GroupRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public List<Group> filter(TypedFilter<Group> filter) {
        return new ArrayList<>();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Class<Group> getEntityClass() {
        return Group.class;
    }
}

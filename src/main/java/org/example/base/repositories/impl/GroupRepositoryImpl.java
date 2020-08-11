package org.example.base.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.example.commons.TypedFilter;
import org.example.base.entities.Group;
import org.example.base.entities.Group_;
import org.example.base.entities.filters.GroupFilter;
import org.example.base.repositories.GroupRepository;
import org.slf4j.Logger;

@Singleton
public class GroupRepositoryImpl implements GroupRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Inject
    private Logger logger;

    @Override
    public Logger getLogger() {
        return null;
    }
}

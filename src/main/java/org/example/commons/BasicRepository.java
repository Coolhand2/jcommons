package org.example.commons;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.SingularAttribute;
import org.hibernate.Session;

public interface BasicRepository<T> {
    EntityManager getEntityManager();
    Class<T> getEntityClass();

    default Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }
}

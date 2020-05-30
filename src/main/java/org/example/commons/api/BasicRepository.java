package org.example.commons.api;

import javax.persistence.EntityManager;
import org.hibernate.Session;

public interface BasicRepository<T> {
    EntityManager getEntityManager();
    Class<T> getEntityClass();

    default Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }
}

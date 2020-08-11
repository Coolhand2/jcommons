package org.example.commons.producers;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerProducer {

    @Produces
    private EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory("AppDB").createEntityManager();
    }

    private void destroy(@Disposes final EntityManager em){

    }
}

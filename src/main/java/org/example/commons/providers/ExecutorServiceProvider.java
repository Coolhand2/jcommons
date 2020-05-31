package org.example.commons.providers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

public class ExecutorServiceProvider {

    @Produces
    private ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool();
    }

    private void destroy(@Disposes final ExecutorService es){

    }
}

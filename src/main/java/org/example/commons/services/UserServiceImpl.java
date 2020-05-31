package org.example.commons.services;

import javax.inject.Inject;
import org.example.commons.repositories.api.UserRepository;
import org.example.commons.services.api.UserService;

public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository users;

    @Override
    public UserRepository getUserRepository() {
        return users;
    }
}

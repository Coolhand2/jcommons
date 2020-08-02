package org.example.commons.services;

import javax.inject.Inject;

import org.example.commons.repositories.api.UserRepository;
import org.example.commons.services.api.MailService;
import org.example.commons.services.api.UserService;
import org.example.commons.utilities.api.MailUtility;
import org.example.commons.utilities.api.SecurityUtility;

public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository users;

    @Inject
    private SecurityUtility security;

    @Inject
    private MailService mail;

    @Override
    public UserRepository getUserRepository() {
        return users;
    }

    @Override
    public SecurityUtility getSecurityUtility() {
        return security;
    }

    @Override
    public MailService getMailService() {
        return mail;
    }
}

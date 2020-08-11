package org.example.base.services.impl;

import javax.ejb.Singleton;
import javax.inject.Inject;

import org.example.base.repositories.UserRepository;
import org.example.base.services.MailService;
import org.example.base.services.UserService;
import org.example.commons.utilities.SecurityUtility;

@Singleton
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

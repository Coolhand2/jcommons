package org.example.base.services.impl;

import javax.ejb.Singleton;
import javax.inject.Inject;
import org.example.base.services.MailService;
import org.example.commons.utilities.MailUtility;

@Singleton
public class MailServiceImpl implements MailService {

    @Inject
    private MailUtility mail;

    @Override
    public MailUtility getMailUtility() {
        return mail;
    }
}

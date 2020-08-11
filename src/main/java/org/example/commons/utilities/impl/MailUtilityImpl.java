package org.example.commons.utilities.impl;

import javax.ejb.Singleton;
import org.example.base.repositories.ConfigurationRepository;
import org.example.commons.utilities.MailUtility;

import javax.inject.Inject;

@Singleton
public class MailUtilityImpl implements MailUtility {

    @Inject
    private ConfigurationRepository configurationRepository;

    @Override
    public ConfigurationRepository getConfigurationRepository() {
        return configurationRepository;
    }
}

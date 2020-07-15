package org.example.commons.utilities;

import org.example.commons.repositories.api.ConfigurationRepository;
import org.example.commons.utilities.api.MailUtility;

import javax.inject.Inject;

public class MailUtilityImpl implements MailUtility {

    @Inject
    private ConfigurationRepository configurationRepository;

    @Override
    public ConfigurationRepository getConfigurationRepository() {
        return configurationRepository;
    }
}

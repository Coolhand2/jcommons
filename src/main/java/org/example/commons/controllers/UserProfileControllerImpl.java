package org.example.commons.controllers;

import javax.inject.Inject;
import org.example.commons.controllers.api.UserProfileController;
import org.example.commons.services.api.UserService;

public class UserProfileControllerImpl implements UserProfileController {

    @Inject
    private UserService userService;

    @Override
    public UserService getUserService() {
        return userService;
    }
}

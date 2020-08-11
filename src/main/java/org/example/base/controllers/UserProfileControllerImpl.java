package org.example.base.controllers;

import javax.inject.Inject;
import org.example.base.controllers.api.UserProfileController;
import org.example.base.services.UserService;

public class UserProfileControllerImpl implements UserProfileController {

    @Inject
    private UserService userService;

    @Override
    public UserService getUserService() {
        return userService;
    }
}

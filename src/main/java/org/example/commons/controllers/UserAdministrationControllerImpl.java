package org.example.commons.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.example.commons.controllers.api.UserAdministrationController;
import org.example.commons.entities.User;
import org.example.commons.entities.filters.UserFilter;
import org.example.commons.services.api.GroupService;
import org.example.commons.services.api.UserService;

public final class UserAdministrationControllerImpl implements UserAdministrationController {

    @Inject
    private transient UserService userService;

    @Inject
    private transient GroupService groupService;

    private List<User> userList = new ArrayList<>();

    private UserFilter userFilter = UserFilter.builder().build();

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public GroupService getGroupService() {
        return groupService;
    }

    @Override
    public List<User> getUserList() {
        return userList;
    }

    @Override
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public UserFilter getUserFilter() {
        return userFilter;
    }
}

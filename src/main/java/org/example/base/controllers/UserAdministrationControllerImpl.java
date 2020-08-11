package org.example.base.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.example.base.controllers.api.UserAdministrationController;
import org.example.base.entities.User;
import org.example.base.entities.filters.UserFilter;
import org.example.base.services.GroupService;
import org.example.base.services.UserService;

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

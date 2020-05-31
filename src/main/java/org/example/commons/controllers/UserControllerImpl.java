package org.example.commons.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import org.example.commons.controllers.api.UserController;
import org.example.commons.entities.User;
import org.example.commons.entities.filters.UserFilter;
import org.example.commons.services.api.UserService;

public final class UserControllerImpl implements UserController {

    @Inject
    private transient UserService userService;

    @Inject
    private transient ExecutorService executorService;

    private boolean searchingFlag = false;

    private boolean savingFlag = false;

    private List<User> userList = new ArrayList<>();

    private UserFilter userFilter = UserFilter.builder().build();

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public ExecutorService getExecutorService() {
        return executorService;
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
    public boolean getSearchingFlag() {
        return searchingFlag;
    }

    @Override
    public void setSearchingFlag(boolean flag) {
        searchingFlag = flag;
    }

    @Override
    public boolean getSavingFlag() {
        return savingFlag;
    }

    @Override
    public void setSavingFlag(boolean flag) {
        savingFlag = flag;
    }

    @Override
    public UserFilter getUserFilter() {
        return userFilter;
    }
}

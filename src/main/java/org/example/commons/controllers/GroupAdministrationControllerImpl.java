package org.example.commons.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.example.commons.controllers.api.GroupAdministrationController;
import org.example.commons.entities.Group;
import org.example.commons.entities.filters.GroupFilter;
import org.example.commons.services.api.GroupService;

public class GroupAdministrationControllerImpl implements GroupAdministrationController {

    @Inject
    private GroupService groupService;

    private Group newGroup = new Group();

    private GroupFilter groupFilter = new GroupFilter();

    private List<Group> groupList = new ArrayList<>();

    @Override
    public GroupService getGroupService() {
        return groupService;
    }

    @Override
    public Group getNewGroup() {
        return newGroup;
    }

    @Override
    public void setNewGroup(Group group) {
        newGroup = group;
    }

    @Override
    public GroupFilter getGroupFilter() {
        return groupFilter;
    }

    @Override
    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    @Override
    public List<Group> getGroupList() {
        return groupList;
    }
}

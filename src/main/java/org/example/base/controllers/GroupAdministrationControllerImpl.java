package org.example.base.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.example.base.controllers.api.GroupAdministrationController;
import org.example.base.entities.Group;
import org.example.base.entities.filters.GroupFilter;
import org.example.base.services.GroupService;

public class GroupAdministrationControllerImpl implements GroupAdministrationController {

    @Inject
    private GroupService groupService;

    private Group newGroup = new Group();

    private GroupFilter groupFilter = GroupFilter.builder().build();

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

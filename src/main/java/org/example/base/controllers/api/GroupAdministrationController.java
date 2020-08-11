package org.example.base.controllers.api;

import java.util.List;
import org.example.base.entities.Group;
import org.example.base.entities.filters.GroupFilter;
import org.example.base.services.GroupService;

public interface GroupAdministrationController extends SessionController {
    GroupService getGroupService();

    Group getNewGroup();

    void setNewGroup(Group group);

    default void saveNewGroup() {
        getGroupService().saveNewGroup(getNewGroup());
        setNewGroup(Group.builder().build());
    }

    GroupFilter getGroupFilter();

    default void fetchGroupList() {
        setGroupList(getGroupService().searchGroups(getGroupFilter()));
    }

    void setGroupList(List<Group> groupList);

    List<Group> getGroupList();

    default void disableGroup(Group group) {
        if(!getGroupList().contains(group)) {
            throw new IllegalArgumentException("Must only edit groups that you've searched for!");
        }
        getGroupService().deleteAllMembershipsForGroup(group);
    }

    default void enableGroup(Group group) {
        if(!getGroupList().contains(group)) {
            throw new IllegalArgumentException("Must only edit groups that you've searched for!");
        }
        getGroupService().addUserToGroup(getSessionUser(), group);
    }

    default void beginEditingGroup(Group group) {
        if(!getGroupList().contains(group)) {
            throw new IllegalArgumentException("Must only edit groups that you've searched for!");
        }
        group.setEditing(true);
    }

    default void finishEditingGroup(Group group) {
        if(!getGroupList().contains(group)) {
            throw new IllegalArgumentException("Must only edit groups that you've searched for!");
        }
        group.setEditing(false);
        getGroupService().updateGroup(group);
    }

    default void saveGroup(Group group) {
        if(!getGroupList().contains(group)) {
            throw new IllegalArgumentException("Must only edit groups that you've searched for!");
        }
        getGroupService().updateGroup(group);
    }
}

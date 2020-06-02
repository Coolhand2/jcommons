package org.example.commons.controllers.api;

import java.util.List;
import org.example.commons.entities.Group;
import org.example.commons.entities.filters.GroupFilter;
import org.example.commons.services.api.GroupService;

public interface GroupAdministrationController extends SessionController {
    GroupService getGroupService();

    Group getNewGroup();
    void setNewGroup(Group group);
    default void saveNewGroup() {
        getGroupService().saveNewGroup(getNewGroup());
        setNewGroup(new Group());
    }

    GroupFilter getGroupFilter();
    default void fetchGroupList() {
        setGroupList(getGroupService().searchGroups(getGroupFilter()));
    }
    void setGroupList(List<Group> groupList);
    List<Group> getGroupList();

    default void disableGroup(Group group) {
        getGroupService().deleteAllMembershipsForGroup(group);
    }
    default void enableGroup(Group group) {
        getGroupService().addUserToGroup(getSessionUser(), group);
    }
    default void beginEditingGroup(Group group){
        group.setEditing(true);
    }
    default void finishEditingGroup(Group group) {
        group.setEditing(false);
        getGroupService().updateGroup(group);
    }
    default void saveGroup(Group group) {
        getGroupService().updateGroup(group);
    }
}

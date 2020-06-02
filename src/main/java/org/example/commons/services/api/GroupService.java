package org.example.commons.services.api;

import java.util.List;
import org.example.commons.entities.Group;
import org.example.commons.entities.Membership;
import org.example.commons.entities.User;
import org.example.commons.entities.filters.GroupFilter;
import org.example.commons.repositories.api.GroupRepository;
import org.example.commons.repositories.api.MembershipRepository;

public interface GroupService {
    GroupRepository getGroupRepository();
    MembershipRepository getMembershipRepository();

    default void saveNewGroup(Group newGroup) {
        getGroupRepository().create(newGroup);
    }

    default List<Group> searchGroups(GroupFilter groupFilter) {
        return getGroupRepository().filter(groupFilter);
    }

    default void deleteAllMembershipsForGroup(Group group) {
        getMembershipRepository().delete(group.getMemberships());
    }

    default void addUserToGroup(User user, Group group) {
        Membership membership = Membership.builder().user(user).group(group).build();
        getMembershipRepository().create(membership);
    }

    default void updateGroup(Group group) {
        getGroupRepository().update(group);
    }
}

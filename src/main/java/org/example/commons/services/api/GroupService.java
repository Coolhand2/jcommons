package org.example.commons.services.api;

import java.util.List;
import java.util.stream.Collectors;
import org.example.commons.entities.Group;
import org.example.commons.entities.Membership;
import org.example.commons.entities.User;
import org.example.commons.entities.filters.GroupFilter;
import org.example.commons.repositories.api.GroupRepository;
import org.example.commons.repositories.api.MembershipRepository;

public interface GroupService {

    GroupRepository getGroupRepository();

    default void saveNewGroup(Group newGroup) {
        getGroupRepository().create(newGroup);
    }

    default List<Group> searchGroups(GroupFilter groupFilter) {
        return getGroupRepository().filter(groupFilter);
    }

    default void deleteAllMembershipsForGroup(Group group) {
        group.getMemberships().clear();
        getGroupRepository().update(group);
    }

    default void addUserToGroup(User user, Group group) {
        group.addMember(user);
        getGroupRepository().update(group);
    }

    default void updateGroup(Group group) {
        getGroupRepository().update(group);
    }

    default void removeUserFromGroups(User user) {
        List<Membership> memberships = getMembershipRepository().findByUser(user);
        List<Group> groups = memberships.parallelStream().map(m -> m.getGroup()).collect(Collectors.toList());
        groups.forEach((g) -> g.getMemberships().removeAll(memberships));
        getGroupRepository().update(groups);
    }

    MembershipRepository getMembershipRepository();
}

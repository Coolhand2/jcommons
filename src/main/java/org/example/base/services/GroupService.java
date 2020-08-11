package org.example.base.services;

import java.util.List;
import java.util.stream.Collectors;
import org.example.base.entities.Group;
import org.example.base.entities.Membership;
import org.example.base.entities.MembershipKey;
import org.example.base.entities.User;
import org.example.base.entities.filters.GroupFilter;
import org.example.base.repositories.GroupRepository;
import org.example.base.repositories.MembershipRepository;

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
        List<Group> groups = memberships.parallelStream().map(Membership::getId).map(MembershipKey::getGroup).collect(Collectors.toList());
        groups.forEach(g -> g.removeMember(user));
        getGroupRepository().update(groups);
    }

    default Group getGroupById(Long id) {
        return getGroupRepository().findById(id);
    }
}

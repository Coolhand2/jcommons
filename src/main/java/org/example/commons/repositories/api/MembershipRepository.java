package org.example.commons.repositories.api;

import java.util.List;
import java.util.Map;

import org.example.commons.api.TypedRepository;
import org.example.commons.entities.Group;
import org.example.commons.entities.Membership;
import org.example.commons.entities.MembershipKey;
import org.example.commons.entities.GroupRole;
import org.example.commons.entities.MembershipType;
import org.example.commons.entities.Membership_;
import org.example.commons.entities.User;

public interface MembershipRepository extends TypedRepository<Membership, MembershipKey> {
    default List<Membership> findByGroup(Group group, GroupRole role) {
        return findByColumn(Membership_.group, group);
    }

    default List<Membership> findByUser(User user) {
        return findByColumn(Membership_.user, user);
    }

    default Membership findByUserAndGroup(User user, Group group) {
        return findOneByColumns(Map.ofEntries(
                Map.entry(Membership_.user, List.of(user)),
                Map.entry(Membership_.group, List.of(group))
        ));
    }
}

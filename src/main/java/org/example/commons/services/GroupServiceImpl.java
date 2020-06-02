package org.example.commons.services;

import javax.inject.Inject;
import org.example.commons.repositories.api.GroupRepository;
import org.example.commons.repositories.api.MembershipRepository;
import org.example.commons.services.api.GroupService;

public class GroupServiceImpl implements GroupService {

    @Inject
    private GroupRepository groupRepository;

    @Inject
    private MembershipRepository membershipRepository;

    @Override
    public GroupRepository getGroupRepository() {
        return groupRepository;
    }

    @Override
    public MembershipRepository getMembershipRepository() {
        return membershipRepository;
    }
}

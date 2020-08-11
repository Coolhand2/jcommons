package org.example.base.services.impl;

import javax.ejb.Singleton;
import javax.inject.Inject;
import org.example.base.repositories.GroupRepository;
import org.example.base.repositories.MembershipRepository;
import org.example.base.services.GroupService;

@Singleton
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

package org.example.base.services;

import java.util.List;
import javax.inject.Inject;
import org.example.base.entities.Group;
import org.example.base.entities.User;
import org.example.base.entities.UserRole;
import org.example.base.entities.filters.GroupFilter;
import org.example.base.repositories.GroupRepository;
import org.example.base.repositories.UserRepository;
import org.example.base.repositories.UserRoleRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class GroupServiceTest {

    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.example")
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private GroupRepository groups;

    @Inject
    private UserRepository users;

    @Inject
    private UserRoleRepository roles;

    @Inject
    private GroupService groupService;

    private Group g1, g2, g3;

    private User u1, u2, u3;

    @Before
    public void setup() {
        UserRole r1 = UserRole.builder().build();
        roles.create(r1);

        u1 = User.builder().role(r1).build();
        u2 = User.builder().role(r1).build();
        u3 = User.builder().role(r1).build();
        users.create(u1, u2, u3);

        g1 = Group.builder().name("ABC").description("ZYX").build();
        g2 = Group.builder().name("CDE").description("XWV").build();
        g3 = Group.builder().name("EFG").description("VTU").build();
        groups.create(g1, g2, g3);
    }

    @Test
    public void testAddUserToGroup() {
        assertTrue(g1.getMemberships().isEmpty());
        groupService.addUserToGroup(u1, g1);
        groupService.addUserToGroup(u2, g1);
        assertEquals(g1.getMemberships().size(), 2);
    }

    @Test
    public void testSaveNewGroup() {
        Group g4 = Group.builder().build();
        groupService.saveNewGroup(g4);
        List<Group> allGroups = groups.findAll();
        assertTrue(allGroups.contains(g4));
    }

    @Test
    public void testUpdateGroup() {
        g3.setDescription("SOMETHING COMPLETELY DIFFERENT");
        groupService.updateGroup(g3);
        Group g4 = groups.findById(g3.getId());
        assertEquals(g3, g4);
    }

    @Test
    public void testDeleteAllMembershipsFromGroup() {
        groupService.addUserToGroup(u1, g2);
        groupService.addUserToGroup(u2, g2);
        groupService.addUserToGroup(u3, g2);
        assertEquals(g2.getMemberships().size(), 3);

        groupService.deleteAllMembershipsForGroup(g2);
        assertEquals(g2.getMemberships().size(), 0);
    }

    @Test
    public void testGroupSearch() {
        GroupFilter filter = GroupFilter.builder().name("C").description("Z").build();
        List<Group> groupList = groupService.searchGroups(filter);
        assertTrue(groupList.contains(g1));
        assertEquals(1, groupList.size());
    }

    @Test
    public void testRemoveUserFromGroups() {
        groupService.addUserToGroup(u1, g2);
        groupService.addUserToGroup(u2, g2);
        groupService.addUserToGroup(u3, g2);
        assertEquals(3, g2.getMemberships().size());

        groupService.removeUserFromGroups(u1);
        g2 = groupService.getGroupById(g2.getId());
        assertEquals(2, g2.getMemberships().size());
    }
}

package org.example.commons.services;

import java.util.List;
import javax.inject.Inject;
import org.example.commons.entities.User;
import org.example.commons.entities.UserRole;
import org.example.commons.entities.filters.UserFilter;
import org.example.commons.repositories.api.UserRepository;
import org.example.commons.repositories.api.UserRoleRepository;
import org.example.commons.services.api.UserService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class UserServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceTest.class);

    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.example")
                .addAsResource("META-INF/test-persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private UserRepository users;

    @Inject
    private UserRoleRepository userRoles;

    @Inject
    private UserService userService;

    private User u1, u2, u3;

    private UserRole r1;

    @Before
    public void setup() {
        r1 = UserRole.builder().build();
        userRoles.create(r1);

        u1 = User.builder().username("ABC").role(r1).build();
        u2 = User.builder().username("CDE").role(r1).build();
        u3 = User.builder().username("EFG").role(r1).build();
        users.create(u1, u2, u3);
    }

    @Test
    public void testSearchUsers() {
        UserFilter filter = UserFilter.builder().username("C").build();
        List<User> actual = userService.searchUsers(filter);
        assertEquals(2, actual.size());
        assertTrue(actual.contains(u1));
        assertTrue(actual.contains(u2));
    }

    @Test
    public void testSaveUser() {
        User u4 = u1.copy();
        assertEquals(u4, u1);
        u1.setUsername("Something Completely Different");
        userService.saveUser(u1);
        assertNotEquals(u4, u1);
    }
}

package org.example.commons.services;

import java.util.List;
import javax.inject.Inject;
import org.example.commons.entities.User;
import org.example.commons.entities.filters.UserFilter;
import org.example.commons.repositories.api.UserRepository;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Arquillian.class)
public class UserServiceTest {

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
    private UserService userService;

    private User u1, u2, u3;

    @Before
    public void setup() {
        u1 = User.builder().username("ABC").build();
        u2 = User.builder().username("CDE").build();
        u3 = User.builder().username("EFG").build();
        users.create(u1, u2, u3);
    }

    @Test
    public void testSearchUsers() {
        UserFilter filter = UserFilter.builder().username("C").build();
        List<User> expected = List.of(u1, u2);
        List<User> actual = userService.searchUsers(filter);
        assertEquals(actual, expected);
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

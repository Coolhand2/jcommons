package org.example.commons.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.example.commons.controllers.api.UserController;
import org.example.commons.entities.User;
import org.example.commons.repositories.api.UserRepository;
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

@RunWith(Arquillian.class)
public class UserControllerTest {

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
    private UserController controller;

    User u1, u2, u3;

    @Before
    public void setup() {
        u1 = User.builder().username("ABC").build();
        u2 = User.builder().username("CDE").build();
        u3 = User.builder().username("EFG").build();
        users.create(u1, u2, u3);
    }

    @Test
    public void testUserSearch() {
        //WHEN
        // I type a "C" into the user filter bar.
        controller.getUserFilter().setUsername("C");

        //WHEN
        // Hit the "Search" button.
        controller.fetchUserList();

        //THEN
        // A list of partial-matching users should appear.
        List<User> actual = controller.getUserList();
        List<User> expected = List.of(u1, u2);
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailureToEdit() {
        Logger log = LoggerFactory.getLogger(UserControllerTest.class);
        //GIVEN
        // I have a list of users that I wish to edit.
        //    This is to prove that u3 is not in the list.
        //    Attempting to edit u3 should result in an Exception.
        controller.getUserFilter().setUsername("C");
        controller.fetchUserList();
        List<User> actual = controller.getUserList();
        List<User> expected = List.of(u1, u2);
        assertEquals(expected, actual);

        //WHEN
        // I try to edit a user not in the user list.
        controller.beginEditingUser(u3);

        //THEN
        // The IllegalArgumentException should be thrown.
    }


}

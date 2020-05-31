package org.example.commons.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.example.commons.controllers.api.UserController;
import org.example.commons.entities.User;
import org.example.commons.entities.UserStatus;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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
        u1 = User.builder().username("ABC").status(UserStatus.ACTIVE).build();
        u2 = User.builder().username("CDE").status(UserStatus.ACTIVE).build();
        u3 = User.builder().username("EFG").status(UserStatus.DISABLED).build();
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

    @Test
    public void testSuccessfulEdit() {
        //GIVEN
        // I have a list of users that I wish to edit.
        //    This is to prove that u1 is in the list.
        controller.getUserFilter().setUsername("C");
        controller.fetchUserList();
        List<User> actual = controller.getUserList();
        List<User> expected = List.of(u1, u2);
        assertEquals(expected, actual);

        //WHEN
        // I try to edit a user in the list.
        controller.beginEditingUser(u1);

        //THEN
        // The user's "editing" flag should now be true.
        assertTrue(u1.isEditing());
    }

    @Test
    public void testFinishUserEditing() {
        //We make a copy here for the assert later in the test.
        User u4 = u1.copy();

        //GIVEN
        // I have a list of users that I wish to edit,
        // and that I have selected one of them.
        //     We have proven through other tests that this search
        //     will bring up u1 and u2, so calling beginEditingUser
        //     on u1 is valid.
        controller.getUserFilter().setUsername("C");
        controller.fetchUserList();
        controller.beginEditingUser(u1);

        //WHEN
        // I update the user
        u1.setUsername("Something Completely Different");

        //WHEN
        // I hit the "Finish" or "X" or "Done" button. (whatever it ends up being)
        controller.finishEditingUser(u1);

        //THEN
        // The user's editing flag should now be false.
        assertFalse(u1.isEditing());

        //AND
        // Updates should have been saved.
        assertNotEquals(u1, u4);
    }

    @Test
    public void testUserSave() {
        //We make a copy here for the assert later in the test.
        User u4 = u1.copy();

        //GIVEN
        // I am editing a user in the list of users.
        controller.getUserFilter().setUsername("C");
        controller.fetchUserList();
        controller.beginEditingUser(u1);

        //WHEN
        // I update the user.
        u1.setUsername("Something Completely Different");
        controller.saveUserUpdates(u1);

        //THEN
        // The user's editing flag should now be true.
        assertTrue(u1.isEditing());

        //We set the editing flag to false, to capture the original state of the flag,
        // and ensure we're only testing the username difference.
        u1.setEditing(false);

        //AND
        // Updates should have been saved.
        assertNotEquals(u1, u4);
    }

    @Test
    public void testDisableUser() {
        //GIVEN
        // We have a user we wish to disable.
        controller.getUserFilter().setUsername("C");
        controller.getUserFilter().setStatus(List.of(UserStatus.ACTIVE));
        controller.fetchUserList();
        //We virtually "select" u1 here.

        //The "Disable User" button should be viewable.
        assertTrue(u1.isActive());

        //WHEN
        // I select the "Disable User" button.
        // *click*
        controller.disableUser(u1);

        //THEN
        // User should have a status of "Disabled"
        assertTrue(u1.isDisabled());
    }

    @Test
    public void testEnableUser() {
        //We virtually Disable the User before we run the "test'.
        controller.disableUser(u1);

        //GIVEN
        // We have a user we wish to enable.
        controller.getUserFilter().setUsername("C");
        controller.getUserFilter().setStatus(List.of(UserStatus.DISABLED));
        controller.fetchUserList();

        //The "Enable User" button should be viewable.
        assertTrue(u1.isDisabled());

        //WHEN
        // I select the "Enable User" button.
        // *click*
        controller.enableUser(u1);

        //THEN
        // User should have a status of "Enabled".
        assertTrue(u1.isActive());
    }

}

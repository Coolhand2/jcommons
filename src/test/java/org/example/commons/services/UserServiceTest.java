package org.example.commons.services;

import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.example.commons.entities.Configuration;
import org.example.commons.entities.User;
import org.example.commons.entities.UserPermission;
import org.example.commons.entities.UserRole;
import org.example.commons.entities.UserStatus;
import org.example.commons.entities.filters.UserFilter;
import org.example.commons.repositories.api.ConfigurationRepository;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class UserServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceTest.class);

    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.example")
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private UserRepository users;

    @Inject
    private UserRoleRepository userRoles;

    @Inject
    private ConfigurationRepository configs;

    @Inject
    private UserService userService;

    private User u1, u2, u3;

    private UserRole r1;

    @Before
    public void setup() {
        Configuration c1 = Configuration.builder().name("mail.smtp.host").value("").build();
        configs.create(c1);
        r1 = UserRole.builder().build();
        userRoles.create(r1);

        u1 = User.builder().username("ABC").role(r1).pkiDn("DEF").verificationKey("XYZ").status(UserStatus.UNVERIFIED).build();
        u2 = User.builder().username("CDE").role(r1).pkiDn("FGH").verificationKey("WXY").status(UserStatus.UNVERIFIED).build();
        u3 = User.builder().username("EFG").role(r1).pkiDn("HIJ").verificationKey("VWX").status(UserStatus.ACTIVE).build();
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

    @Test
    public void testGrantRoleToUser() {
        UserRole r2 = UserRole.builder().name("XYZ").build();
        userRoles.create(r2);

        userService.grantRoleToUser(r2, u1);
        u1 = userService.getUserById(u1.getId());
        assertEquals(r2, u1.getRole());
        assertTrue(u1.getPermissions().isEmpty());

        UserRole r3 = UserRole.builder().name("DEF").permissionsGranted(List.of(UserPermission.EDIT_GROUP)).build();
        userRoles.create(r3);

        userService.grantRoleToUser(r3, u1);
        u1 = userService.getUserById(u1.getId());
        assertEquals(r3, u1.getRole());
        assertEquals(1, u1.getPermissions().size());
        assertTrue(u1.getPermissions().contains(UserPermission.EDIT_GROUP));
    }

    @Test
    public void testVerifyUser() {
        assertTrue(u3.isActive());
        userService.verifyUser(u3, "XYZ");
        u3 = userService.getUserById(u3.getId());
        assertTrue(u3.isActive());

        assertTrue(u2.isUnverified());
        u2.setPkiDn("ABC");
        userService.verifyUser(u2, "WXY");
        u2 = userService.getUserById(u2.getId());
        assertTrue(u2.isUnverified());

        assertTrue(u1.isUnverified());
        userService.verifyUser(u1, "XYZ");
        u1 = userService.getUserById(u1.getId());
        assertTrue(u1.isActive());
    }

    @Test
    public void testRegisterUser() {
        User u4 = User.builder().role(r1).build();
        userService.registerUser(u4);
        u4 = userService.getUserById(u4.getId());
        assertFalse(u4.getVerificationKey().isBlank());
        LOG.info("Verification Key: {}", u4.getVerificationKey());
    }
}

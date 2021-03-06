package org.example.commons.api;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.example.base.entities.User;
import org.example.base.entities.UserRole;
import org.example.base.entities.User_;
import org.example.base.repositories.UserRepository;
import org.example.base.repositories.UserRoleRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class TypedRepositoryTest {

    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "org.example")
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private UserRepository users;

    @Inject
    private UserRoleRepository userRoles;

    private User u1, u2, u3;

    private UserRole r1;

    @Before
    public void setup() {
        r1 = UserRole.builder().build();
        userRoles.create(r1);
        u1 = User.builder().username("ABC").pkiDn("ZYX").role(r1).build();
        u2 = User.builder().username("CDE").pkiDn("WVU").role(r1).build();
        u3 = User.builder().username("EFG").pkiDn("TSR").role(r1).build();
        users.create(u1, u2, u3);
    }

    @Test
    public void testFindAll() {
        List<User> actual = users.findAll();
        assertEquals(3, actual.size());
        assertTrue(actual.containsAll(Arrays.asList(u1, u2, u3)));
    }

    @Test
    public void testFindById() {
        User actual = users.findById(u1.getId());
        assertEquals(u1, actual);
    }

    @Test
    public void testFindByIds() {
        List<User> actual = users.findByIds(u1.getId(), u2.getId());
        assertEquals(2, actual.size());
        assertFalse(actual.contains(u3));
    }

    @Test
    public void testFindByIdsIterable() {
        List<User> actual = users.findByIds(List.of(u1.getId(), u2.getId()));
        assertEquals(2, actual.size());
        assertFalse(actual.contains(u3));
    }

    @Test
    public void testFindOneByColumn() {
        User actual = users.findOneByColumn(User_.pkiDn, "ZYX");
        User expected = u1.copy();
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByColumn() {
        List<User> actual = users.findByColumn(User_.username, "CDE");
        assertEquals(1, actual.size());
        assertTrue(actual.contains(u2));
    }

    @Test
    public void testFindOneByColumns() {
        User actual = users.findOneByColumns(Map.ofEntries(
                Map.entry((root) -> root.get(User_.username), "ABC"),
                Map.entry((root) -> root.get(User_.pkiDn), "ZYX")
        ));
        User expected = u1.copy();
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByColumns() {
        List<User> actual = users.findByColumns(Map.ofEntries(
                Map.entry((root) -> root.get(User_.username), List.of("ABC")),
                Map.entry((root) -> root.get(User_.pkiDn), List.of("ZYX"))
        ));
        assertEquals(1, actual.size());
        assertTrue(actual.contains(u1));
    }

    @Test
    public void testUpdate() {
        u1.setUsername("ZYX");
        users.update(u1);
        User u3 = users.findById(u1.getId()).copy();
        assertEquals(u1, u3);
        assertNotEquals(u1, u2);
    }


    @Test
    public void testDelete() {
        users.delete(u2);
        List<User> actual = users.findAll();
        assertEquals(2, actual.size());
        assertFalse(actual.contains(u2));
    }

    @Test
    public void testFindKeepsReference() {
        User actual = users.findById(u1.getId());
        assertEquals(u1, actual);
        u1.setUsername("Something Completely Different");
        assertEquals(u1, actual);
    }

    /**
     * Fun note about this test. It fails on the first assert if we depend on Lombok to generate the equals and hashcode
     * methods. The EqualsBuilder.reflectionEquals method from the Apache Commons-Lang package is able to either ignore
     * the right info, or go deep enough into the object to ensure this test actually passes.
     */
    @Test
    public void testFindDoesNotKeepReferenceWithClone() {
        User actual = users.findById(u1.getId()).copy();
        assertEquals(u1, actual);
        u1.setUsername("Something Completely Different");
        assertNotEquals(u1, actual);
    }

}

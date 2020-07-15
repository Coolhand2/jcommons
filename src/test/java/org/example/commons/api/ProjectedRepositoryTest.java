package org.example.commons.api;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.example.commons.entities.User;
import org.example.commons.entities.UserRole;
import org.example.commons.entities.User_;
import org.example.commons.entities.dtos.UserDataTransfer;
import org.example.commons.repositories.api.UserRepository;
import org.example.commons.repositories.api.UserRoleRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class ProjectedRepositoryTest {
    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "org.example")
                .addAsResource("META-INF/test-persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private UserRepository users;

    @Inject
    private UserRoleRepository userRoles;

    private UserDataTransfer udt1, udt2, udt3;

    @Before
    public void setup() {
        UserRole r1 = UserRole.builder().build();
        userRoles.create(r1);

        User u1 = User.builder().username("ABC").pkiDn("ZYX").role(r1).build();
        User u2 = User.builder().username("CDE").pkiDn("XWV").role(r1).build();
        User u3 = User.builder().username("EFG").pkiDn("VUT").role(r1).build();
        users.create(u1, u2, u3);
        udt1 = UserDataTransfer.from(u1);
        udt2 = UserDataTransfer.from(u2);
        udt3 = UserDataTransfer.from(u3);
    }

    @Test
    public void testFindAllProjected() {
        List<UserDataTransfer> list = users.findAllProjected();
        assertEquals(3, list.size());
        assertTrue(list.containsAll(Arrays.asList(udt1, udt2, udt3)));
    }

    @Test
    public void testFindAllProjectedWithComparator() {
        List<UserDataTransfer> expected = List.of(udt1, udt2, udt3);
        List<UserDataTransfer> actual = users.findAllProjected(UserDataTransfer.defaultSort);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindAllProjectedWithPredicate() {
        List<UserDataTransfer> expected = List.of(udt1, udt2);
        List<UserDataTransfer> actual = users.findAllProjected(udt -> udt.getUsername().contains("C"));
        assertEquals(expected, actual);
    }

    @Test
    public void testFindAllProjectedWithComparatorAndPredicate() {
        List<UserDataTransfer> expected = List.of(udt1, udt2);
        List<UserDataTransfer> actual = users.findAllProjected(UserDataTransfer.defaultSort, udt -> udt.getUsername().contains("C"));
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByIdProjected() {
        UserDataTransfer expected = udt1;
        UserDataTransfer actual = users.findByIdProjected(udt1.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByIdsVarargProjected() {
        List<UserDataTransfer> expected = List.of(udt1, udt2);
        List<UserDataTransfer> actual = users.findByIdsProjected(udt1.getId(), udt2.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByIdsIterableProjected() {
        List<UserDataTransfer> expected = List.of(udt1, udt2);
        List<UserDataTransfer> actual = users.findByIdsProjected(List.of(udt1.getId(), udt2.getId()));
        assertEquals(expected, actual);
    }

    @Test
    public void testFindOneByColumnProjected() {
        UserDataTransfer expected = udt1;
        UserDataTransfer actual = users.findOneByColumnProjected(User_.pkiDn, "ZYX");
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByColumnVarargProjected() {
        List<UserDataTransfer> expected = List.of(udt1);
        List<UserDataTransfer> actual = users.findByColumnProjected(User_.pkiDn, "ZYX");
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByColumnIterableProjected() {
        List<UserDataTransfer> expected = List.of(udt1);
        List<UserDataTransfer> actual = users.findByColumnProjected(User_.pkiDn, List.of("ZYX"));
        assertEquals(expected, actual);
    }

    @Test
    public void testFindOneByColumnsProjected() {
        UserDataTransfer expected = udt1;
        UserDataTransfer actual = users.findOneByColumnsProjected(Map.ofEntries(
                Map.entry(User_.username, List.of("ABC")),
                Map.entry(User_.pkiDn, List.of("ZYX"))
        ));
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByColumnsProjected() {
        List<UserDataTransfer> expected = List.of(udt1);
        List<UserDataTransfer> actual = users.findByColumnsProjected(Map.ofEntries(
                Map.entry(User_.username, List.of("ABC")),
                Map.entry(User_.pkiDn, List.of("ZYX"))
        ));
        assertEquals(expected, actual);

    }
}

package org.example.commons.api;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.example.commons.entities.User;
import org.example.commons.entities.UserRole;
import org.example.commons.entities.User_;
import org.example.commons.entities.projections.UserProjection;
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
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private UserRepository users;

    @Inject
    private UserRoleRepository userRoles;

    private UserProjection udt1, udt2, udt3;

    @Before
    public void setup() {
        UserRole r1 = UserRole.builder().build();
        userRoles.create(r1);

        User u1 = User.builder().username("ABC").pkiDn("ZYX").role(r1).build();
        User u2 = User.builder().username("CDE").pkiDn("XWV").role(r1).build();
        User u3 = User.builder().username("EFG").pkiDn("VUT").role(r1).build();
        users.create(u1, u2, u3);
        udt1 = UserProjection.from(u1);
        udt2 = UserProjection.from(u2);
        udt3 = UserProjection.from(u3);
    }

    @Test
    public void testFindAllProjected() {
        List<UserProjection> list = users.findAllProjected();
        assertEquals(3, list.size());
        assertTrue(list.containsAll(Arrays.asList(udt1, udt2, udt3)));
    }

    @Test
    public void testFindAllProjectedWithComparator() {
        List<UserProjection> expected = List.of(udt1, udt2, udt3);
        List<UserProjection> actual = users.findAllProjected(UserProjection.defaultSort);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindAllProjectedWithPredicate() {
        List<UserProjection> expected = List.of(udt1, udt2);
        List<UserProjection> actual = users.findAllProjected(udt -> udt.getUsername().contains("C"));
        assertEquals(expected, actual);
    }

    @Test
    public void testFindAllProjectedWithComparatorAndPredicate() {
        List<UserProjection> expected = List.of(udt1, udt2);
        List<UserProjection> actual = users.findAllProjected(UserProjection.defaultSort, udt -> udt.getUsername().contains("C"));
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByIdProjected() {
        UserProjection expected = udt1;
        UserProjection actual = users.findByIdProjected(udt1.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByIdsVarargProjected() {
        List<UserProjection> expected = List.of(udt1, udt2);
        List<UserProjection> actual = users.findByIdsProjected(udt1.getId(), udt2.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByIdsIterableProjected() {
        List<UserProjection> expected = List.of(udt1, udt2);
        List<UserProjection> actual = users.findByIdsProjected(List.of(udt1.getId(), udt2.getId()));
        assertEquals(expected, actual);
    }

    @Test
    public void testFindOneByColumnProjected() {
        UserProjection expected = udt1;
        UserProjection actual = users.findOneByColumnProjected(User_.pkiDn, "ZYX");
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByColumnVarargProjected() {
        List<UserProjection> expected = List.of(udt1);
        List<UserProjection> actual = users.findByColumnProjected(User_.pkiDn, "ZYX");
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByColumnIterableProjected() {
        List<UserProjection> expected = List.of(udt1);
        List<UserProjection> actual = users.findByColumnProjected(User_.pkiDn, List.of("ZYX"));
        assertEquals(expected, actual);
    }

    @Test
    public void testFindOneByColumnsProjected() {
        UserProjection expected = udt1;
        UserProjection actual = users.findOneByColumnsProjected(Map.ofEntries(
                Map.entry(User_.username, List.of("ABC")),
                Map.entry(User_.pkiDn, List.of("ZYX"))
        ));
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByColumnsProjected() {
        List<UserProjection> expected = List.of(udt1);
        List<UserProjection> actual = users.findByColumnsProjected(Map.ofEntries(
                Map.entry(User_.username, List.of("ABC")),
                Map.entry(User_.pkiDn, List.of("ZYX"))
        ));
        assertEquals(expected, actual);

    }
}

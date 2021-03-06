package org.example.base.repositories;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.example.base.entities.Address;
import org.example.base.entities.PhoneNumber;
import org.example.base.entities.User;
import org.example.base.entities.UserRole;
import org.example.base.entities.UserStatus;
import org.example.base.entities.UserType;
import org.example.base.entities.filters.UserFilter;
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
public class UserRepositoryTest {

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

    private User u1, u2, u3;

    private UserRole r1;

    @Before
    public void setup() {
        r1 = UserRole.builder().build();
        userRoles.create(r1);

        PhoneNumber p1 = PhoneNumber.builder()
                .areaCode("123")
                .frontThree("123")
                .backFour("1234")
                .build();
        PhoneNumber p2 = PhoneNumber.builder()
                .areaCode("567")
                .frontThree("567")
                .backFour("5678")
                .build();
        PhoneNumber p3 = PhoneNumber.builder()
                .areaCode("909")
                .frontThree("909")
                .backFour("9090")
                .build();

        Address a1 = Address.builder()
                .city("ABC")
                .country("ABC")
                .state("ABC")
                .street1("ABC")
                .street2("ABC")
                .zipcode("ABC")
                .build();
        Address a2 = Address.builder()
                .city("DEF")
                .country("DEF")
                .state("DEF")
                .street1("DEF")
                .street2("DEF")
                .zipcode("DEF")
                .build();
        Address a3 = Address.builder()
                .city("GHI")
                .country("GHI")
                .state("GHI")
                .street1("GHI")
                .street2("GHI")
                .zipcode("GHI")
                .build();

        u1 = User.builder()
                .username("ABC")
                .email("ABC")
                .pkiDn("ABC")
                .verificationKey("ABC")
                .phoneNumber(p1)
                .address(a1)
                .role(r1)
                .type(UserType.MEMBER)
                .status(UserStatus.DISABLED)
                .build();

        u2 = User.builder()
                .username("CDE")
                .email("CDE")
                .pkiDn("CDE")
                .verificationKey("CDE")
                .phoneNumber(p2)
                .address(a2)
                .role(r1)
                .type(UserType.GUEST)
                .status(UserStatus.ACTIVE)
                .build();

        u3 = User.builder()
                .username("EFG")
                .email("EFG")
                .pkiDn("EFG")
                .verificationKey("EFG")
                .phoneNumber(p3)
                .address(a3)
                .role(r1)
                .type(UserType.MEMBER)
                .status(UserStatus.UNVERIFIED)
                .build();

        users.create(u1, u2, u3);
    }

    @Test
    public void testFilterById() {
        UserFilter filter = UserFilter.builder().id(String.valueOf(u2.getId())).build();

        List<User> userList = users.filter(filter);
        assertEquals(1, userList.size());
        assertTrue(userList.contains(u2));
    }

    @Test
    public void testFilterByUsername() {
        UserFilter filter = UserFilter.builder().username("D").build();

        List<User> userList = users.filter(filter);
        assertEquals(1, userList.size());
        assertTrue(userList.contains(u2));
    }

    @Test
    public void testFilterByEmail() {
        UserFilter filter = UserFilter.builder().email("D").build();

        List<User> userList = users.filter(filter);
        assertEquals(1, userList.size());
        assertTrue(userList.contains(u2));
    }

    @Test
    public void testFilterByPki() {
        UserFilter filter = UserFilter.builder().pkiDn("D").build();

        List<User> userList = users.filter(filter);
        assertEquals(1, userList.size());
        assertTrue(userList.contains(u2));
    }

    @Test
    public void testFilterByVerification() {
        UserFilter filter = UserFilter.builder().verificationKey("D").build();

        List<User> userList = users.filter(filter);
        assertEquals(1, userList.size());
        assertTrue(userList.contains(u2));
    }

    @Test
    public void testFilterByPhone() {
        UserFilter filter = UserFilter.builder().phoneNumber("6").build();

        List<User> userList = users.filter(filter);
        assertEquals(1, userList.size());
        assertTrue(userList.contains(u2));
    }

    @Test
    public void testFilterByAddress() {
        UserFilter filter = UserFilter.builder().address("D").build();

        List<User> userList = users.filter(filter);
        assertEquals(1, userList.size());
        assertTrue(userList.contains(u2));
    }

    @Test
    public void testFilterByType() {
        UserFilter filter = UserFilter.builder().type(Collections.singletonList(UserType.GUEST)).build();

        List<User> userList = users.filter(filter);
        assertEquals(1, userList.size());
        assertTrue(userList.contains(u2));
    }

    @Test
    public void testFilterByStatus() {
        UserFilter filter = UserFilter.builder().status(Collections.singletonList(UserStatus.ACTIVE)).build();

        List<User> userList = users.filter(filter);
        assertEquals(1, userList.size());
        assertTrue(userList.contains(u2));
    }

    @Test
    public void testFilterWithEmptyFilter() {
        UserFilter filter = UserFilter.builder().build();

        List<User> userList = users.filter(filter);
        List<User> findAllList = users.findAll();
        assertEquals(findAllList, userList);
    }

    @Test
    public void testFilterWithComplicatedFilter() {
        UserFilter filter = UserFilter.builder().email("B").type(Collections.singletonList(UserType.MEMBER)).build();

        List<User> userList = users.filter(filter);
        assertEquals(1, userList.size());
        assertTrue(userList.contains(u1));

        filter.setType(Collections.singletonList(UserType.GUEST));
        userList = users.filter(filter);
        assertTrue(userList.isEmpty());

        filter.setType(Collections.emptyList());
        filter.setEmail("C");
        userList = users.filter(filter);
        assertEquals(2, userList.size());
        assertTrue(userList.contains(u1));
        assertTrue(userList.contains(u2));

        filter.setEmail("D");
        userList = users.filter(filter);
        assertEquals(1, userList.size());
        assertTrue(userList.contains(u2));
    }

    @Test
    public void testFindByUsername() {
        User expected = u1;
        User actual = users.findByUsername("ABC");
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByEmail() {
        User expected = u1;
        User actual = users.findByEmail("ABC");
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByPkiDn() {
        User expected = u1;
        User actual = users.findByPkiDn("ABC");
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByVerificationKey() {
        User expected = u1;
        User actual = users.findByVerificationKey("ABC");
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByPhoneNumber() {
        PhoneNumber p1 = PhoneNumber.builder()
            .areaCode("123")
            .frontThree("123")
            .backFour("1234")
            .build();
        User expected = u1;
        User actual = users.findByPhoneNumber(p1);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByAddress() {
        Address a1 = Address.builder()
                .city("ABC")
                .country("ABC")
                .state("ABC")
                .street1("ABC")
                .street2("ABC")
                .zipcode("ABC")
                .build();
        User expected = u1;
        User actual = users.findByAddress(a1);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByType() {
        List<User> expected = List.of(u2);
        List<User> actual = users.findByType(UserType.GUEST);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByStatus() {
        List<User> expected = List.of(u1);
        List<User> actual = users.findByStatus(UserStatus.DISABLED);
        assertEquals(expected, actual);
    }
}

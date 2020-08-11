package org.example.base.resources;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.example.base.entities.User;
import org.example.base.entities.UserRole;
import org.example.base.repositories.UserRepository;
import org.example.base.repositories.UserRoleRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
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
public class UserManagementResourceTest {

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
    private UserRoleRepository roles;

    @Inject
    private UserManagementResource umr;

    private User u1, u2, u3;

    @Before
    public void setup() {
        UserRole r1 = UserRole.builder().build();
        roles.create(r1);

        u1 = User.builder().role(r1).build();
        u2 = User.builder().role(r1).build();
        u3 = User.builder().role(r1).build();
        users.create(u1, u2, u3);
    }

    @Test
    @RunAsClient
    public void testFetchAllUsers(@ArquillianResteasyResource final WebTarget target) {
        Response response = target.path("/users").request(MediaType.APPLICATION_JSON).get();
        assertEquals(200, response.getStatus());

        List<User> actual = (List<User>) response.getEntity();
        assertEquals(3, actual.size());
    }

}

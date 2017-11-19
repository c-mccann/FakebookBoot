package model.services;

import com.carlmccann2.fakebookboot.DataConfig;
import com.carlmccann2.fakebookboot.ServiceConfig;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.services.UsersService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {DataConfig.class, ServiceConfig.class})
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersServiceTest {

    private Log log = LogFactory.getLog(UsersServiceTest.class);

    @Autowired
    private UsersService usersService;

    @Test
    public void test01_getUserById(){

        Integer id = new Integer(5);
        User user = usersService.getUser(id);
        assertNotNull("test01_getUserById: " + id, user);
        log.info("test01_getUserById: " + user.toString());
        assertEquals(id, user.getId());
        assertEquals("abraham", user.getFirstName());
        assertEquals("lincoln", user.getLastName());
        assertEquals("abethebabe@gmail.com", user.getEmail());
        assertEquals("1337LeEt!",user.getPassword());
        assertEquals(1508185538000L, user.getAccountCreated().getTime());
    }

    @Test
    public void test02_getUserByEmail(){

        String email = "abethebabe@gmail.com";
        User user = usersService.getUser(email);
        Integer i = new Integer(5);
        assertEquals(i, user.getId());
        assertEquals("abraham", user.getFirstName());
        assertEquals("lincoln", user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals("1337LeEt!",user.getPassword());
        assertEquals(1508185538000L, user.getAccountCreated().getTime());

    }


    @Test
    public void test03_getUsers(){

        List<User> users = usersService.getUsers();
        assertTrue("test03_getUsers: empty return", (users.size() > 0) );
        StringBuilder stringBuilder = new StringBuilder();
        users.forEach(u -> stringBuilder.append(u.toString() + ", "));
        log.info("test03_getUsers: " + stringBuilder.toString().substring(0, stringBuilder.length() - 2));
    }

    @Test
    @Transactional
    public void test04_addUser(){

        User user = new User("Test", "User", "testuser@gmail.com", "Testuser1!", Timestamp.valueOf(LocalDateTime.now()));
        usersService.addUser(user);

        User user1 = usersService.getUser(user.getEmail());

        assertNotNull("test05_addUser: return null", user1);
        log.info("test05_addUser: " + user1.toString());

    }

    @Test
    @Transactional
    public void test05_removeUser(){

    }
}

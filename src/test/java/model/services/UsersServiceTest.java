package model.services;

import com.carlmccann2.fakebookboot.DataConfig;
import com.carlmccann2.fakebookboot.FakebookBootApplication;
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
@ContextConfiguration(classes = {DataConfig.class, ServiceConfig.class, FakebookBootApplication.class})
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersServiceTest {

    private Log log = LogFactory.getLog(UsersServiceTest.class);

    @Autowired
    private UsersService usersService;

    @Test
    public void test01_getUser(){

        String email = "carlmccann2@gmail.com";
        User user = usersService.getUser(email);
        assertNotNull("test01_getUserByEmail: " + user.toString(), user);
        log.info("test01_getUserByEmail: " + user.toString());

        Integer id = new Integer(1);
        assertEquals(id, user.getId());
        assertEquals("Carl", user.getFirstName());
        assertEquals("McCann", user.getLastName());
        assertEquals("carlmccann2@gmail.com", user.getEmail());
        assertEquals("to be encrypted",user.getPassword());
    }

    @Test
    public void test02_getUser(){

        Integer id = new Integer(1);
        User user = usersService.getUser(id);
        assertNotNull("test02_getUserById: " + user.toString(), user);
        log.info("test02_getUserById: " + user.toString());

        assertEquals(id, user.getId());
        assertEquals("Carl", user.getFirstName());
        assertEquals("McCann", user.getLastName());
        assertEquals("carlmccann2@gmail.com", user.getEmail());
        assertEquals("to be encrypted",user.getPassword());
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

        assertNotNull("test04_addUser: return null", user1);
        log.info("test04_addUser: " + user1.toString());

    }

    @Test
    @Transactional
    public void test05_registerUser(){

        User user = new User("Test", "User", "testuser@gmail.com", "Testuser1!", null);
        usersService.registerUser(user);

        User user1 = usersService.getUser(user.getEmail());

        assertNotNull("test05_registerUser: return null", user1);
        log.info("test05_registerUser: " + user1.toString());

    }

    @Test
    @Transactional
    public void test06_removeUser(){
        User user = usersService.getUser(21);
        usersService.removeUser(user.getId());
        assertNull(usersService.getUser(21));
    }

    @Test
    public void test07_getUsersByNameNearestMatch(){
        String name = "fake";
        List<User> users = usersService.getUsersByNameNearestMatch(name);
        assertNotNull("nothing returned", users);
        assertTrue("wrong number of objects returned", users.size() == 2);
    }





}

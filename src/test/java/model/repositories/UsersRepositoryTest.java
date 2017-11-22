package model.repositories;

import com.carlmccann2.fakebookboot.DataConfig;
import com.carlmccann2.fakebookboot.FakebookBootApplication;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.repositories.UsersRepository;
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

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {DataConfig.class, FakebookBootApplication.class})
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersRepositoryTest {

    private Log log = LogFactory.getLog(UsersRepositoryTest.class);
    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void test01_getUserByEmail(){
        String email = "carlmccann2@gmail.com";
        User user = usersRepository.getUserByEmail(email);
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
    public void test02_getUserById(){
        Integer id = new Integer(1);
        User user = usersRepository.getUserById(id);
        assertNotNull("test02_getUserById: " + user.toString(), user);
        log.info("test02_getUserById: " + user.toString());

        assertEquals(id, user.getId());
        assertEquals("Carl", user.getFirstName());
        assertEquals("McCann", user.getLastName());
        assertEquals("carlmccann2@gmail.com", user.getEmail());
        assertEquals("to be encrypted",user.getPassword());

    }



//    @Query(value = "SELECT * FROM users WHERE CONCAT(first_name, ' ', last_name) RLIKE ?1", nativeQuery = true)
//    List<User> getUsersByNameNearestMatch(String fullName);
//
    @Test
    public void test03_getUsersByNameNearestMatch(){
        String name = "fake";
        List<User> users = usersRepository.getUsersByNameNearestMatch(name);
        assertNotNull("nothing returned", users);
        assertTrue("wrong number of objects returned", users.size() == 2);


    }


}

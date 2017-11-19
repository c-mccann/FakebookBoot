package model.repositories;

import com.carlmccann2.fakebookboot.DataConfig;
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

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = DataConfig.class)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersRepositoryTest {

    private Log log = LogFactory.getLog(UsersRepositoryTest.class);
    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void test01_getUserByEmail(){
        String email = "abethebabe@gmail.com";
        User user = usersRepository.getUserByEmail(email);
        assertNotNull("test01_getUserByEmail: " + user.toString(), user);
        log.info("test01_getUserByEmail: " + user.toString());

        Integer i = new Integer(5);
        assertEquals(i, user.getId());
        assertEquals("abraham", user.getFirstName());
        assertEquals("lincoln", user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals("1337LeEt!",user.getPassword());
        assertEquals(1508185538000L, user.getAccountCreated().getTime());


    }

    @Test
    public void test02_getUserById(){
        Integer id = 5;
        User user = usersRepository.getUserById(id);
        assertNotNull("test02_getUserById: " + user.toString(), user);
        log.info("test02_getUserById: " + user.toString());

        assertEquals(id, user.getId());
        assertEquals("abraham", user.getFirstName());
        assertEquals("lincoln", user.getLastName());
        assertEquals("abethebabe@gmail.com", user.getEmail());
        assertEquals("1337LeEt!",user.getPassword());
        assertEquals(1508185538000L, user.getAccountCreated().getTime());


    }


}

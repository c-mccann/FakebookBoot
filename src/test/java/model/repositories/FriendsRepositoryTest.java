package model.repositories;

import com.carlmccann2.fakebookboot.DataConfig;
import com.carlmccann2.fakebookboot.FakebookBootApplication;
import com.carlmccann2.fakebookboot.model.orm.Friend;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.repositories.FriendsRepository;
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
public class FriendsRepositoryTest {

    private Log log = LogFactory.getLog(FriendsRepositoryTest.class);
    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void test01_getAllByUserOne(){
        User user = usersRepository.getOne(2);
        List<Friend> friends = friendsRepository.getAllByUserOne(user);

        assertTrue("test01_getAllByUserOne: nothing returned", friends.size() > 0);
        StringBuilder stringBuilder = new StringBuilder();
        friends.forEach(f -> stringBuilder.append(f.toString() + ", "));
        log.info("test01_getAllByUserOne: " + stringBuilder.toString().substring(0, stringBuilder.length() - 2));
    }

    @Test
    public void test02_getAllByUserTwo(){
        User user = usersRepository.getOne(3);
        List<Friend> friends = friendsRepository.getAllByUserTwo(user);

        assertTrue("test01_getAllByUserTwo: nothing returned", friends.size() > 0);
        StringBuilder stringBuilder = new StringBuilder();
        friends.forEach(f -> stringBuilder.append(f.toString() + ", "));
        log.info("test02_getAllByUserTwo: " + stringBuilder.toString().substring(0, stringBuilder.length() - 2));
    }

    @Test
    public void test03_getAllByUserOneAndFriendsSinceIsNull(){
        fail("to be implemented");
        friendsRepository.getAllByUserOneAndFriendsSinceIsNull(new User());
    }

    @Test
    public void test04_getAllByUserTwoAndFriendsSinceIsNull(){
        fail("to be implemented");
        friendsRepository.getAllByUserTwoAndFriendsSinceIsNull(new User());
    }

    @Test
    public void test05_getByUserOneAndUserTwoAndFriendsSinceIsNotNull(){
        User userOne = usersRepository.getUserById(7);
        User userTwo = usersRepository.getUserById(16);
        Friend friend = friendsRepository.getByUserOneAndUserTwoAndFriendsSinceIsNotNull(userOne, userTwo);
        assertNotNull("friend object is null", friend);
        assertEquals("friendship_id does not match,", friend.getFriendshipId(), new Integer(8));
    }

}

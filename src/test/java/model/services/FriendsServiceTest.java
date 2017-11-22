package model.services;


import com.carlmccann2.fakebookboot.DataConfig;
import com.carlmccann2.fakebookboot.FakebookBootApplication;
import com.carlmccann2.fakebookboot.ServiceConfig;
import com.carlmccann2.fakebookboot.model.orm.Friend;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.services.FriendsService;
import com.carlmccann2.fakebookboot.model.services.UsersService;
import model.repositories.FriendsRepositoryTest;
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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {DataConfig.class, ServiceConfig.class, FakebookBootApplication.class})
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FriendsServiceTest {

    private Log log = LogFactory.getLog(FriendsRepositoryTest.class);

    @Autowired
    private FriendsService friendsService;

    @Autowired
    private UsersService usersService;
    /*

    getAllByUserOne(User userOne);

    List<Friend> getAllByUserTwo(User userTwo);

    List<Friend> getAllByUserOneAndFriendsSinceIsNull(User userOne);

    List<Friend> getAllByUserTwoAndFriendsSinceIsNull(User userTwo);

    Friend getByUserOneAndUserTwoAndFriendsSinceIsNotNull(User userOne, User userTwo);

    Friend getByUserOneAndUserTwoAndFriendsSinceIsNull(User userOne, User userTwo);

    boolean addFriend(Friend friend);

    boolean removeFriend(Friend friend);
     */
    @Test
    public void test01_getAllByUserOne(){
        User user = usersService.getUser(1);
        List<Friend> friends = friendsService.getAllByUserOne(user);
        assertTrue("nothing returned", friends.size() > 0);

    }
    @Test
    public void test02_getAllByUserTwo(){
        User user = usersService.getUser(1);
        List<Friend> friends = friendsService.getAllByUserTwo(user);
        assertTrue("nothing returned", friends.size() > 0);
    }
    @Test
    public void test03_getAllByUserOneAndFriendsSinceIsNull(){
        User user = usersService.getUser(8);
        List<Friend> sentFriendRequests = friendsService.getAllByUserOneAndFriendsSinceIsNull(user);
        assertTrue("nothing returned", sentFriendRequests.size() > 0);
    }
    @Test
    public void test04_getAllByUserTwoAndFriendsSinceIsNull(){
        User user = usersService.getUser(21);
        List<Friend> friendRequests = friendsService.getAllByUserTwoAndFriendsSinceIsNull(user);
        assertTrue("nothing returned", friendRequests.size() > 0);
    }
    @Test
    public void test05_getByUserOneAndUserTwoAndFriendsSinceIsNotNull(){
        User userOne = usersService.getUser(2);
        User userTwo = usersService.getUser(3);
        Friend friend = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNotNull(userOne, userTwo);
        assertNotNull("nothing returned", friend);
        assertEquals("id doesnt match", new Integer(1), friend.getFriendshipId());
    }
    @Test
    public void test06_getByUserOneAndUserTwoAndFriendsSinceIsNull(){
        User userOne = usersService.getUser(8);
        User userTwo = usersService.getUser(21);
        Friend friend = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNull(userOne, userTwo);
        assertNotNull("nothing returned", friend);
        assertEquals("id doesnt match", new Integer(12), friend.getFriendshipId());
    }
    @Test
    @Transactional
    public void test07_addFriend(){
        User userOne = usersService.getUser(1);
        User userTwo = usersService.getUser(21);
        Friend friend = new Friend(userOne, userTwo, null, "test");
        friendsService.addFriend(friend);
        Friend returnFriend = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNull(userOne, userTwo);
        assertNotNull("nothing returned", returnFriend);

    }
    @Test
    @Transactional
    public void test08_removeFriend(){
        User userOne = usersService.getUser(1);
        User userTwo = usersService.getUser(21);
        Friend friend = new Friend(userOne, userTwo, null, "test");
        friendsService.addFriend(friend);
        Friend returnFriend = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNull(userOne, userTwo);
        assertNotNull("nothing returned", returnFriend);
        friendsService.removeFriend(returnFriend);
        assertNull("still exists", friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNull(userOne, userTwo));
    }

}

package model.services;


import com.carlmccann2.fakebookboot.DataConfig;
import com.carlmccann2.fakebookboot.FakebookBootApplication;
import com.carlmccann2.fakebookboot.ServiceConfig;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.services.LikesService;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {DataConfig.class, ServiceConfig.class, FakebookBootApplication.class})
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LikesServiceTest {

    private Log log = LogFactory.getLog(LikesServiceTest.class);
    @Autowired
    private LikesService likesService;
    @Autowired
    private UsersService usersService;


    /*
    void likePost(Integer postId, User user);

     void likeComment(Integer commentId, User user);
     */
    @Test
    public void test01_likePost(){
        int initialSize = likesService.getAll().size();

        User user = usersService.getUser(21);
        likesService.likePost(1, user);
        assertEquals("not liked", initialSize + 1, likesService.getAll().size());

        // unlike
        likesService.likePost(1, user);
        assertEquals("not unliked", initialSize, likesService.getAll().size());


    }
    @Test
    public void test02_likeComment(){
        int initialSize = likesService.getAll().size();

        User user = usersService.getUser(21);
        likesService.likeComment(1, user);
        assertEquals("not liked", initialSize + 1, likesService.getAll().size());

        // unlike
        likesService.likeComment(1, user);
        assertEquals("not unliked", initialSize, likesService.getAll().size());
    }
}
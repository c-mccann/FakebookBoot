package model.services;


import com.carlmccann2.fakebookboot.DataConfig;
import com.carlmccann2.fakebookboot.FakebookBootApplication;
import com.carlmccann2.fakebookboot.ServiceConfig;
import com.carlmccann2.fakebookboot.model.orm.Comment;
import com.carlmccann2.fakebookboot.model.orm.Post;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.repositories.CommentsRepository;
import com.carlmccann2.fakebookboot.model.services.CommentsService;
import com.carlmccann2.fakebookboot.model.services.PostsService;
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
import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {DataConfig.class, ServiceConfig.class, FakebookBootApplication.class})
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommentsServiceTest {
    private Log log = LogFactory.getLog(CommentsServiceTest.class);
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private PostsService postsService;
    @Autowired
    private UsersService usersService;

    @Test
    @Transactional
    public void test01_addComment(){

        int initialSize = commentsService.getAll().size();
        Post post = postsService.getPost(1);
        User user = usersService.getUser(21);

        Comment comment = new Comment(post, user, "test comment", new Timestamp(System.currentTimeMillis()), new HashSet<>());
        commentsService.addComment(comment);

        assertEquals("getAll didn't return 1 extra object", initialSize + 1, commentsService.getAll().size());
    }

    @Test
    @Transactional
    public void test02_getAll(){
        assertTrue("nothing returned", commentsService.getAll().size() > 0);
    }
}

package model.services;

import com.carlmccann2.fakebookboot.DataConfig;
import com.carlmccann2.fakebookboot.FakebookBootApplication;
import com.carlmccann2.fakebookboot.ServiceConfig;
import com.carlmccann2.fakebookboot.model.orm.Post;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.services.PostsService;
import com.carlmccann2.fakebookboot.model.services.UsersService;
import model.repositories.PostsRepositoryTest;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {DataConfig.class, ServiceConfig.class, FakebookBootApplication.class})
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostsServiceTest {
    private Log log = LogFactory.getLog(PostsRepositoryTest.class);
    @Autowired
    private PostsService postsService;
    @Autowired
    UsersService usersService;




    @Test
    public void test01_getPost(){
        Post post = postsService.getPost(1);

        assertNotNull("nothing returned", post);
        assertEquals("post_text doesnt match", "Honey, I'm home", post.getPostText());
    }

    @Test
    public void test02_getAllByUser(){
        User user = usersService.getUser(1);
        List<Post> posts = postsService.getAllByUser(user);
        assertTrue("nothing returned", posts.size() > 0);

    }

    @Test
    public void test03_getAllByUserPostedTo(){
        User user = usersService.getUser(1);
        List<Post> posts = postsService.getAllByUserPostedTo(user);
        assertTrue("nothing returned", posts.size() > 0);
    }
    @Test
    public void test04_getAllForUserFeed(){
        User user = usersService.getUser(1);
        List<Post> posts = postsService.getAllForUserFeed(user);
        assertTrue("nothing returned", posts.size() > 0);
    }

    @Test
    public void test05_getAll(){
        List<Post> posts = postsService.getAll();
        assertTrue("nothing returned", posts.size() > 0);

    }

    @Test
    @Transactional
    public void test06_addPost(){
        int initialSize = postsService.getAll().size();
        User user = usersService.getUser(21);
        Post post = new Post(user, user, "test post", null, new Timestamp(System.currentTimeMillis()), new ArrayList<>(), new ArrayList<>());
        postsService.addPost(post);
        assertEquals("not added", initialSize + 1, postsService.getAll().size());
    }

    @Test
    @Transactional
    public void test07_deletePost(){
        int initialSize = postsService.getAll().size();
        User user = usersService.getUser(21);
        Post post = new Post(user, user, "test post", null, new Timestamp(System.currentTimeMillis()), new ArrayList<>(), new ArrayList<>());
        postsService.addPost(post);
        assertEquals("not added", initialSize + 1, postsService.getAll().size());
        //added, now to delete
        List<Post> posts = postsService.getAll();
        Collections.sort(posts, Comparator.comparing(Post::getPostCreated).reversed());

        postsService.deletePost(posts.get(posts.size() - 1));

        assertEquals("not deleted", initialSize, postsService.getAll().size());

    }

}

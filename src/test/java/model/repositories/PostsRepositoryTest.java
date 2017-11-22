package model.repositories;

import com.carlmccann2.fakebookboot.DataConfig;
import com.carlmccann2.fakebookboot.FakebookBootApplication;
import com.carlmccann2.fakebookboot.model.orm.Post;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.repositories.PostsRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {DataConfig.class, FakebookBootApplication.class})
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostsRepositoryTest {

    private Log log = LogFactory.getLog(PostsRepositoryTest.class);
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    UsersRepository usersRepository;


    @Test
    @Transactional
    public void test01_getAllByUser(){
        User user = usersRepository.findOne(1);
        List<Post> posts = postsRepository.getAllByUser(user);
        assertTrue("test01_getAllByUser: nothing returned",posts.size() > 0);
        StringBuilder stringBuilder = new StringBuilder();
        posts.forEach(p -> stringBuilder.append(p.toString() + ", "));
        log.info("test01_getAllByUser: " + stringBuilder.toString().substring(0, stringBuilder.length() - 2));

    }

    @Test
    @Transactional
    public void test02_getAllByUserPostedTo(){
        User user = usersRepository.findOne(1);
        List<Post> posts = postsRepository.getAllByUserPostedTo(user);
        assertTrue("test02_getAllByUserPostedTo: nothing returned",posts.size() > 0);
        StringBuilder stringBuilder = new StringBuilder();
        posts.forEach(p -> stringBuilder.append(p.toString() + ", "));
        log.info("test02_getAllByUserPostedTo: " + stringBuilder.toString());
    }



}

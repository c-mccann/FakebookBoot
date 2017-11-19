package model.repositories;

import com.carlmccann2.fakebookboot.DataConfig;
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
@ContextConfiguration(classes = DataConfig.class)
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
        User user = usersRepository.getUserByEmail("abethebabe@gmail.com");
        List<Post> posts = postsRepository.getAllByUser(user);
        assertTrue("test01_getAllByUser: nothing returned",posts.size() > 0);
        StringBuilder stringBuilder = new StringBuilder();
        posts.forEach(p -> stringBuilder.append(p.toString() + ", "));
        log.info("test01_getAllByUser: " + stringBuilder.toString().substring(0, stringBuilder.length() - 2));

    }

    @Test
    @Transactional
    public void test02_findOne(){
        Post post = postsRepository.findOne(1);
        assertNotNull("test02_getPostByPostId: is null", post);
        log.info("test02_getPostByPostId: " + post.toString());
    }

}

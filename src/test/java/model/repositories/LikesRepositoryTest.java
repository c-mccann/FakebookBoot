package model.repositories;

import com.carlmccann2.fakebookboot.DataConfig;
import com.carlmccann2.fakebookboot.FakebookBootApplication;
import com.carlmccann2.fakebookboot.model.orm.Comment;
import com.carlmccann2.fakebookboot.model.orm.Like;
import com.carlmccann2.fakebookboot.model.orm.Post;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.repositories.CommentsRepository;
import com.carlmccann2.fakebookboot.model.repositories.LikesRepository;
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
public class LikesRepositoryTest {

    private Log log = LogFactory.getLog(LikesRepository.class);

    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Test
    @Transactional
    public void test01_getAllByPost(){
        Post post = postsRepository.findOne(1);
        List<Like> likes = likesRepository.getAllByPost(post);

        assertTrue("test01_getAllByPost: empty return", likes.size() > 0);
        StringBuilder stringBuilder = new StringBuilder();
        likes.forEach(l -> stringBuilder.append(l.toString() + ", "));
        log.info("test01_getAllByPost: " + stringBuilder.toString().substring(0, stringBuilder.length() - 2));


    }

    @Test
    public void test02_getAllByComment(){
        Comment comment = commentsRepository.getOne(1);
        List<Like> likes = likesRepository.getAllByComment(comment);

        assertTrue("test02_getAllByComment: empty return", likes.size() > 0);
        StringBuilder stringBuilder = new StringBuilder();
        likes.forEach(l -> stringBuilder.append(l.toString() + ", "));
        log.info("test02_getAllByComment: " + stringBuilder.toString().substring(0, stringBuilder.length() - 2));

    }

    @Test
    public void test03_getByUserAndPost(){
        User user = usersRepository.getUserById(3);
        Post post = postsRepository.findOne(1);
        Like like = likesRepository.getByUserAndPost(user, post);
        assertNotNull(like);
        assertEquals("like_id doesnt match", new Integer(1),like.getLikeId());
    }

    @Test
    public void test04_getByUserAndComment(){
        User user = usersRepository.getUserById(2);
        Comment comment = commentsRepository.findOne(1);
        Like like = likesRepository.getByUserAndComment(user, comment);
        assertNotNull(like);
        assertEquals("like_id doesnt match", new Integer(2),like.getLikeId());

    }


}

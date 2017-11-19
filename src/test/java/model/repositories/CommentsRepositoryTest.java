package model.repositories;

import com.carlmccann2.fakebookboot.DataConfig;
import com.carlmccann2.fakebookboot.model.orm.Comment;
import com.carlmccann2.fakebookboot.model.orm.Post;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.repositories.CommentsRepository;
import com.carlmccann2.fakebookboot.model.repositories.PostsRepository;
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
public class CommentsRepositoryTest {

    private Log log = LogFactory.getLog(CommentsRepositoryTest.class);
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private PostsRepository postsRepository;

    @Test
    @Transactional
    public void test01_getAllByPost(){
        Post post = postsRepository.findOne(1);

        List<Comment> comments = commentsRepository.getAllByPost(post);

        assertTrue("test01_getAllByPost: nothing returned", comments.size() > 0);
        StringBuilder stringBuilder = new StringBuilder();
        comments.forEach(c -> stringBuilder.append(c.toString() + ", "));
        log.info("test01_getAllByPost: " + stringBuilder.toString().substring(0, stringBuilder.length() - 2));
    }


}

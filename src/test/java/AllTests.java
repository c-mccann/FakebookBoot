import com.carlmccann2.fakebookboot.FakebookBootApplicationTests;
import com.carlmccann2.fakebookboot.model.repositories.CommentsRepository;
import controller.UsersControllerTest;
import model.repositories.*;
import model.services.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Suite.class)
@SpringBootTest
@Suite.SuiteClasses({
        // repos
        CommentsRepositoryTest.class,
        FriendsRepositoryTest.class,
        LikesRepositoryTest.class,
        PhotosRepositoryTest.class,
        PostsRepositoryTest.class,
        UsersRepositoryTest.class,

        // services

        CommentsServiceTest.class,
        FriendsServiceTest.class,
        LikesServiceTest.class,
        PhotosServiceTest.class,
        PostsServiceTest.class,
        UsersServiceTest.class,

        // controllers

        UsersControllerTest.class,
        // other
        FakebookBootApplicationTests.class
})

@ActiveProfiles("test")
public final class AllTests {


}

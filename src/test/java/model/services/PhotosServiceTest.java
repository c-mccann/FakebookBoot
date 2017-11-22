package model.services;


import com.carlmccann2.fakebookboot.DataConfig;
import com.carlmccann2.fakebookboot.FakebookBootApplication;
import com.carlmccann2.fakebookboot.ServiceConfig;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {DataConfig.class, ServiceConfig.class, FakebookBootApplication.class})
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhotosServiceTest {

    @Test
    public void test01_(){
        fail("no methods exist");
    }
}

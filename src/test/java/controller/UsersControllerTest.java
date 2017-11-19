package controller;

import com.carlmccann2.fakebookboot.DataConfig;
import com.carlmccann2.fakebookboot.FakebookBootApplication;
import com.carlmccann2.fakebookboot.ServiceConfig;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.services.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest // this and @WebMvcTest are mutually exclusive
@ContextConfiguration(classes = {DataConfig.class, ServiceConfig.class, FakebookBootApplication.class})//, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    private Log log = LogFactory.getLog(UsersControllerTest.class);


    final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private UsersService usersService;



    @Test
    public void test01_getAll(){
        List<User> users = usersService.getUsers();
        String url = "http://localhost:8080/fakebook/users/all";

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            String responseBody =  response.getBody();

            List<User> users2 = objectMapper.readValue(responseBody, new TypeReference<List<User>>(){});

            assertEquals("test01_getAll: request != service", users.toString(), users2.toString());
            log.info("test01_getUsers: " + users2.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    @Test
    public void test02_addUser(){
        String url = "http://localhost:8080/fakebook/users/add";
        User user = new User("test_user", "controller", "tuc@gmail.com","Testtest1!", new Timestamp(System.currentTimeMillis()));
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String requestJson = objectMapper.writeValueAsString(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
            String answer = restTemplate.postForObject(url, entity, String.class);
            System.out.println(answer);


            User user2 =  usersService.getUser(user.getEmail());

            assertEquals("test03_getUserByEmail: request != service", user.getFirstName(), user2.getFirstName());
            assertEquals("test03_getUserByEmail: request != service", user.getLastName(), user2.getLastName());
            assertEquals("test03_getUserByEmail: request != service", user.getEmail(), user2.getEmail());
            assertEquals("test03_getUserByEmail: request != service", user.getPassword(), user2.getPassword());



        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void test03_getUserByEmail(){
        String email = "abethebabe@gmail.com";
        String url = "http://localhost:8080/fakebook/users/";
        User user = usersService.getUser(email);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ResponseEntity<String> response = restTemplate.exchange(url + email, HttpMethod.GET, null, String.class);
            String responseBody =  response.getBody();

            User user2 = objectMapper.readValue(responseBody, User.class);

            assertEquals("test03_getUserByEmail: request != service", user.toString(), user2.toString());



            log.info("test03_getUserByEmail: " + user2.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test
    public void test04_removeUser(){
        User user =  usersService.getUser("tuc@gmail.com");

        String url = "http://localhost:8080/fakebook/users/remove/";

        restTemplate.delete(url + user.getId());

        assertNull("test_04_removeUser: user still exists", usersService.getUser("tuc@gmail.com"));
        log.info("test_04_removeUser: " + user.toString());


    }
}

package com.carlmccann2.fakebookboot.controller;


import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.repositories.UsersRepository;
import com.carlmccann2.fakebookboot.model.services.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private Log log = LogFactory.getLog(UsersController.class);

    @Autowired
    private UsersService usersService;


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        log.info("getAllUsers()");
        return usersService.getUsers();
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addUser(@RequestBody final User user){
        log.info("addUser(): " + user.toString());
        usersService.addUser(user);
    }

    @GetMapping(value = "/{email:.+}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public User getUserByEmail(@PathVariable String email) {
        log.info("getUserByEmail(): " + email);

//        User user = usersRepository.getUserByEmail(email);
        User user = usersService.getUser(email);
        if(user != null){
            log.info("return: " + user.toString());
        }
        else{
            log.info("return: " + null);
        }

        return user;
    }

    @DeleteMapping(value = "/remove/{userId}")
    public void removeUser(@PathVariable Integer userId){
        log.info("removeUser(): " + userId);
        usersService.removeUser(userId);
    }

    @GetMapping(value = "/search/{fullName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsersByNameNearestMatch(@PathVariable String fullName){
        log.info("getUsersByNameNearestMatch(): " + fullName);
        return usersService.getUsersByNameNearestMatch(fullName);
    }


    @GetMapping(value = "/loggedin", produces = MediaType.APPLICATION_JSON_VALUE)
    public User loggedIn(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        log.info("loggedIn(): " + user);
        return user;
    }

}

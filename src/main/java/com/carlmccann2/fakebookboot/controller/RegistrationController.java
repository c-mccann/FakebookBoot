package com.carlmccann2.fakebookboot.controller;

import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.repositories.UsersRepository;
import com.carlmccann2.fakebookboot.model.services.MailService;
import com.carlmccann2.fakebookboot.model.services.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private Log log = LogFactory.getLog(RegistrationController.class);
    @Autowired
    private MailService mailService;

    @Autowired
    private UsersService usersService;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public  boolean signUp(@RequestBody String userJson){
        log.info("signUp(): " + userJson);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = null;
        try {
            user = objectMapper.readValue(userJson, User.class);
            if(usersService.addUser(user)){
                mailService.sendEmailOnUserRegistration(usersService.getUser(user.getEmail()));
                return true;
            }
            else return false;


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/activate/{userId}")
    public String signUpSuccess(@PathVariable Integer userId){
        log.info("signUpSuccess(): " + userId);
        User user = usersService.getUser(userId);
        if(user.getAccountCreated() == null){
            if(usersService.registerUser(user)){
                return "Account activated";
            }
            return "error";
        }
        else{
            return "Already activated";
        }

    }
}

package com.carlmccann2.fakebookboot.controller;


import com.carlmccann2.fakebookboot.model.orm.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RequestMapping("/")
@RestController
public class FakebookController {

    private Log log = LogFactory.getLog(FakebookController.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @RequestMapping(value = "/")
    public ModelAndView fakebook() {
        log.info("fakebook()");
        return new ModelAndView("fakebook");
    }

    @RequestMapping(value = "/login-success", method = RequestMethod.POST)
    public boolean loginSuccess(HttpServletRequest request, @RequestBody User user) {
        log.info("loginSuccess(): " + user.toString());

        request.getSession().setAttribute("user", user);
        if (request.getSession().getAttribute("user") != null) {
            log.info("\tuser object from session: " + request.getSession().getAttribute("user").toString());
            return true;
        }
        log.info("\terror storing user in session");
        return false;
    }

    @RequestMapping(value = "/logout")
    public boolean logout(HttpServletRequest request) {
        log.info("logout()");
        request.getSession().removeAttribute("user");
        log.info("\tremoved user object from session");
        return request.getSession().getAttribute("user") == null;
    }

    @RequestMapping(value = "/login")
    public ModelAndView login() {
        log.info("login()");
        return new ModelAndView("login");
    }


    @RequestMapping(value = "/register")
    public String register() {
        log.info("register()");
        return "register";
    }

    @RequestMapping(value = "/activity-feed")
    public String feed() {
        log.info("feed()");
        return "activity-feed";
    }

    @RequestMapping(value = "/profile")
    public String profile() {
        log.info("profile()");
        return "profile";
    }
}

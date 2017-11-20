package com.carlmccann2.fakebookboot.controller;


import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.services.LikesService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/likes")
public class LikesController {

    private Log log = LogFactory.getLog(LikesController.class);

    @Autowired
    private LikesService likesService;

    @PostMapping("/post/{postId}")
    public void likePost(@PathVariable Integer postId, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        log.info("likePost(): userId: " + user.getId() + ", postId: " + postId);
        likesService.likePost(postId, user);
    }


    @PostMapping(value = "/comment/{commentId}")
    public void likeComment(@PathVariable Integer commentId, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        log.info("likeComment(): userId: " + user.getId() + ", commentId: " + commentId);
        likesService.likeComment(commentId, user);
    }


}

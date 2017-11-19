package com.carlmccann2.fakebookboot.controller;

import com.carlmccann2.fakebookboot.model.orm.Like;
import com.carlmccann2.fakebookboot.model.repositories.CommentsRepository;
import com.carlmccann2.fakebookboot.model.repositories.LikesRepository;
import com.carlmccann2.fakebookboot.model.repositories.PostsRepository;
import com.carlmccann2.fakebookboot.model.repositories.UsersRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikesController {

    private Log log = LogFactory.getLog(LikesController.class);

    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private CommentsRepository commentsRepository;

    @PostMapping("/post/{userId}/{postId}")
    public void likePost(@PathVariable Integer userId, @PathVariable Integer postId){
        Like like = new Like(usersRepository.findOne(userId), postsRepository.findOne(postId), null);
        likesRepository.save(like);
    }


    @PostMapping(value = "/comment/{userId}/{commentId}")
    public void likeComment(@PathVariable Integer userId, @PathVariable Integer commentId){
        Like like = new Like(usersRepository.findOne(userId), null, commentsRepository.findOne(commentId));
        likesRepository.save(like);
    }


}

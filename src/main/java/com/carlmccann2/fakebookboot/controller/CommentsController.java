package com.carlmccann2.fakebookboot.controller;

import com.carlmccann2.fakebookboot.model.orm.Comment;
import com.carlmccann2.fakebookboot.model.orm.Like;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.repositories.CommentsRepository;
import com.carlmccann2.fakebookboot.model.repositories.PostsRepository;
import com.carlmccann2.fakebookboot.model.repositories.UsersRepository;
import com.carlmccann2.fakebookboot.model.services.CommentsService;
import com.carlmccann2.fakebookboot.model.services.PostsService;
import com.carlmccann2.fakebookboot.model.services.UsersService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    private Log log = LogFactory.getLog(CommentsController.class);

    @Autowired
    private CommentsService commentsService;
    @Autowired
    private PostsService postsService;
    @Autowired
    private UsersService usersService;

    //TODO: fix commented


//
//    @GetMapping("/{postId}")
//    public List<Comment> getCommentsByPostId(@PathVariable Integer postId){
//        return commentsService.getAllByPost(postsRepository.findOne(postId));
//
//    }
//
    @PostMapping(value = "/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addComment(@RequestBody Comment comment, @PathVariable Integer postId, HttpServletRequest request){
        if( comment.getPost() == null){
            comment.setPost(postsService.getPost(postId));
        }
        if(comment.getUser() == null){
            User user = (User) request.getSession().getAttribute("user");
            comment.setUser(user);
        }
        log.info("addComment(): " + comment.getCommentId());
        log.info("addComment(): " + comment.getCommentText());
        log.info("addComment(): " + comment.getCommentCreated().toString());
        log.info("addComment(): " + comment.getUser());
        log.info("addComment(): " + comment.getPost());
        log.info("addComment(): " + comment.getLikes());



        commentsService.addComment(comment);
    }
//
//    @DeleteMapping("/{commentId}")
//    public void deleteComment(@PathVariable Integer commentId){
//        Comment comment = commentsRepository.getByPostAndUser(postsRepository.findOne(postId),
//                usersRepository.findOne(userId));
//
//        commentsService.deleteComment(commentId);
//    }

}

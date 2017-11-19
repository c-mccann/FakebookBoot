package com.carlmccann2.fakebookboot.controller;

import com.carlmccann2.fakebookboot.model.orm.Comment;
import com.carlmccann2.fakebookboot.model.orm.Like;
import com.carlmccann2.fakebookboot.model.orm.Post;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.services.PostsService;
import com.carlmccann2.fakebookboot.model.services.UsersService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostsController {

    private Log log = LogFactory.getLog(PostsController.class);

    @Autowired
    private UsersService usersService;
    @Autowired
    private PostsService postsService;

    @GetMapping(value = "/all")
    public List<Post> getAll() {
        log.info("getAll()");
        return postsService.getAll();
    }

//    @GetMapping(value = "/{userId}")
//    public List<Post> getAllByUser(@PathVariable Integer userId){
//        return postsRepository.getAllByUser(usersRepository.getOne(userId));
//    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public void addPost(@RequestBody Post post, HttpServletRequest request){
        if(post.getUser() == null){
            User user = (User) request.getSession().getAttribute("user");
            post.setUser(user);
        }
        log.info("addPost()" + post.toString());
        postsService.addPost(post);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Integer postId){
        log.info("deletePost()");
        postsService.deletePost(postsService.getPost(postId));
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getAllPostsToUser(@PathVariable Integer userId){
        log.info("getAllPostsToUser(): " + userId);
        User user = usersService.getUser(userId);
        return postsService.getAllByUserPostedTo(user);
    }

    @GetMapping(value = "/feed/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getAllPostsForUserFeed(@PathVariable Integer userId){
        log.info("getAllPostsForUserFeed(): " + userId);
        User user = usersService.getUser(userId);
        return postsService.getAllForUserFeed(user);
    }
}

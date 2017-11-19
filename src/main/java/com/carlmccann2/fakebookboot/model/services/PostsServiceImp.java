package com.carlmccann2.fakebookboot.model.services;


import com.carlmccann2.fakebookboot.model.orm.Comment;
import com.carlmccann2.fakebookboot.model.orm.Friend;
import com.carlmccann2.fakebookboot.model.orm.Post;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.repositories.FriendsRepository;
import com.carlmccann2.fakebookboot.model.repositories.PostsRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class PostsServiceImp implements PostsService{

    private Log log = LogFactory.getLog(PostsServiceImp.class);

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    FriendsRepository friendsRepository;

    @Override
    public List<Post> getAllByUser(User user) {
        log.info("getAllByUser(): " + user.toString());
        return postsRepository.getAllByUser(user);
    }


    @Override
    public List<Post> getAllByUserPostedTo(User user) {
        log.info("getAllByUserPostedTo(): " + user.toString());
        List<Post> posts = postsRepository.getAllByUserPostedTo(user);
        for(Post p: posts){
            // sort comments into newest last
            Collections.sort(p.getComments(),Comparator.comparing(Comment::getCommentCreated));
        }

        // sort posts into newest first
        Collections.sort(posts, Comparator.comparing(Post::getPostCreated).reversed());
        return posts;
    }

    @Override
    public List<Post> getAll() {
        log.info("getAll()");
        return postsRepository.findAll();
    }

    @Override
    public void addPost(Post post) {
        log.info("addPost()");
        postsRepository.save(post);
    }

    @Override
    public Post getPost(Integer id) {
        log.info("getPost()");
        return postsRepository.findOne(id);
    }

    @Override
    public void deletePost(Post post) {
        log.info("deletePost()");
        postsRepository.delete(post);
    }

    @Override
    public List<Post> getAllForUserFeed(User user) {
        log.info("getAllForUserFeed()");

        Set<Friend> friends = new HashSet<>();

        friends.addAll(friendsRepository.getAllByUserOne(user));
        friends.addAll(friendsRepository.getAllByUserTwo(user));

        List<Post> feedPosts = new ArrayList<>();

        for(Friend f: friends){
            User friend = (user.equals(f.getUserOne())) ? f.getUserTwo() : f.getUserOne();
            feedPosts.addAll(postsRepository.getAllByUserPostedTo(friend));
        }
        feedPosts.addAll(postsRepository.getAllByUser(user));
        Collections.sort(feedPosts, Comparator.comparing(Post::getPostCreated).reversed());
        return feedPosts;
    }
}

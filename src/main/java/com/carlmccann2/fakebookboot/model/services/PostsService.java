package com.carlmccann2.fakebookboot.model.services;

import com.carlmccann2.fakebookboot.model.orm.Post;
import com.carlmccann2.fakebookboot.model.orm.User;

import java.util.List;

public interface PostsService {

    Post getPost(Integer id);

    List<Post> getAllByUser(User user);

    List<Post> getAllByUserPostedTo(User user);

    List<Post> getAllForUserFeed(User user);

    List<Post> getAll();

    public void addPost(Post post);

    public void deletePost(Post post);

}

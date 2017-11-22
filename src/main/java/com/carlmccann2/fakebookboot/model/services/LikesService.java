package com.carlmccann2.fakebookboot.model.services;

import com.carlmccann2.fakebookboot.model.orm.Like;
import com.carlmccann2.fakebookboot.model.orm.User;

import java.util.List;

public interface LikesService {

     List<Like> getAll();

     void likePost(Integer postId, User user);

     void likeComment(Integer commentId, User user);

}

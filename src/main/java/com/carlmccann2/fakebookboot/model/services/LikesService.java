package com.carlmccann2.fakebookboot.model.services;

import com.carlmccann2.fakebookboot.model.orm.User;

public interface LikesService {

     void likePost(Integer postId, User user);

     void likeComment(Integer commentId, User user);

}

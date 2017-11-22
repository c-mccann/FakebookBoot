package com.carlmccann2.fakebookboot.model.services;


import com.carlmccann2.fakebookboot.model.orm.Comment;

import java.util.List;

public interface CommentsService {

    public void addComment(Comment comment);

    public List<Comment> getAll();
}

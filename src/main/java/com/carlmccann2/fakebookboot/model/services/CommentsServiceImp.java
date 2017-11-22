package com.carlmccann2.fakebookboot.model.services;


import com.carlmccann2.fakebookboot.model.orm.Comment;
import com.carlmccann2.fakebookboot.model.repositories.CommentsRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CommentsServiceImp implements CommentsService{
    private Log log = LogFactory.getLog(CommentsServiceImp.class);

    @Autowired
    private CommentsRepository commentsRepository;
    @Override
    public void addComment(Comment comment) {
        log.info("addComment(): " + comment.getCommentText());
        commentsRepository.save(comment);
    }

    @Override
    public List<Comment> getAll() {
        log.info("getAll(): ");
        return commentsRepository.findAll();
    }
}

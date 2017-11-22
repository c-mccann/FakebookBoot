package com.carlmccann2.fakebookboot.model.services;


import com.carlmccann2.fakebookboot.model.orm.Comment;
import com.carlmccann2.fakebookboot.model.orm.Like;
import com.carlmccann2.fakebookboot.model.orm.Post;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.repositories.CommentsRepository;
import com.carlmccann2.fakebookboot.model.repositories.LikesRepository;
import com.carlmccann2.fakebookboot.model.repositories.PostsRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LikesServiceImp implements LikesService {

    private Log log = LogFactory.getLog(LikesServiceImp.class);

    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public void likePost(Integer postId, User user) {
        log.info("likePost(): userId: " + user.getId() + ", postId: " + postId);
        Post post = postsRepository.findOne(postId);
        Like like = new Like(user,post,null);
        Like oldLike = likesRepository.getByUserAndPost(user, post);
        if(oldLike == null){
            log.info("likePost(): userId: " + user.getId() + ", commentId: " + postId);
            likesRepository.save(like);
        }
        else{
            log.info("likePost(): (unlike) userId: " + user.getId() + ", commentId: " + postId);
            likesRepository.delete(oldLike);
        }
    }

    @Override
    public void likeComment(Integer commentId, User user) {
        Comment comment = commentsRepository.findOne(commentId);
        Like like = new Like(user, null, comment);
        Like oldLike = likesRepository.getByUserAndComment(user, comment);
        if(oldLike != null){ // flipped logic due to intellij duplicate code warning
            log.info("likeComment(): (unlike) userId: " + user.getId() + ", commentId: " + commentId);
            likesRepository.delete(oldLike);
        }
        else{
            log.info("likeComment(): userId: " + user.getId() + ", commentId: " + commentId);
            likesRepository.save(like);
        }

    }

    @Override
    public List<Like> getAll() {
        log.info("getAll()");
        return likesRepository.findAll();
    }
}

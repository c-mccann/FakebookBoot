package com.carlmccann2.fakebookboot.model.repositories;

import com.carlmccann2.fakebookboot.model.orm.Comment;
import com.carlmccann2.fakebookboot.model.orm.Like;
import com.carlmccann2.fakebookboot.model.orm.Post;
import com.carlmccann2.fakebookboot.model.orm.User;
import javafx.geometry.Pos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Like, Integer> {

    List<Like> getAllByPost(Post post);

    List<Like> getAllByComment(Comment comment);

    Like getByUserAndPost(User user, Post post);

    Like getByUserAndComment(User user, Comment comment);
}

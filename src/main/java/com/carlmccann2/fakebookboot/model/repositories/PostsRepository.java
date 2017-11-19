package com.carlmccann2.fakebookboot.model.repositories;

import com.carlmccann2.fakebookboot.model.orm.Post;
import com.carlmccann2.fakebookboot.model.orm.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post, Integer> {

    List<Post> getAllByUser(User user);

    List<Post> getAllByUserPostedTo(User user);

}

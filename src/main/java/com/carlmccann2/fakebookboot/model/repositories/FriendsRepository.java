package com.carlmccann2.fakebookboot.model.repositories;

import com.carlmccann2.fakebookboot.model.orm.Friend;
import com.carlmccann2.fakebookboot.model.orm.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendsRepository extends JpaRepository<Friend, Integer> {

    List<Friend> getAllByUserOne(User userOne);

    List<Friend> getAllByUserTwo(User userTwo);

    List<Friend> getAllByUserOneAndFriendsSinceIsNull(User userOne);

    List<Friend> getAllByUserTwoAndFriendsSinceIsNull(User userTwo);

    Friend getByUserOneAndUserTwoAndFriendsSinceIsNotNull(User userOne, User userTwo);





}

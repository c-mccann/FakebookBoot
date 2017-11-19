package com.carlmccann2.fakebookboot.model.services;

import com.carlmccann2.fakebookboot.model.orm.Friend;
import com.carlmccann2.fakebookboot.model.orm.User;

import java.util.List;

public interface FriendsService {

    List<Friend> getAllByUserOne(User userOne);

    List<Friend> getAllByUserTwo(User userTwo);

    List<Friend> getAllByUserOneAndFriendsSinceIsNull(User userOne);

    List<Friend> getAllByUserTwoAndFriendsSinceIsNull(User userTwo);

    Friend getByUserOneAndUserTwo(User userOne, User userTwo);

    boolean addFriend(Friend friend);

    boolean removeFriend(Friend friend);

}

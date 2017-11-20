package com.carlmccann2.fakebookboot.model.services;


import com.carlmccann2.fakebookboot.model.orm.Friend;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.repositories.FriendsRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FriendsServiceImp implements FriendsService {

    private Log log = LogFactory.getLog(FriendsServiceImp.class);
    @Autowired
    private FriendsRepository friendsRepository;
    @Override
    public List<Friend> getAllByUserOne(User userOne) {
        if(userOne == null) log.info("getAllByUserOne(): " + null);
        else                log.info("getAllByUserOne(): " + userOne.toString());
        return friendsRepository.getAllByUserOne(userOne);
    }

    @Override
    public List<Friend> getAllByUserTwo(User userTwo) {
        if(userTwo == null) log.info("getAllByUserTwo(): " + null);
        else                log.info("getAllByUserTwo(): " + userTwo.toString());
        return friendsRepository.getAllByUserTwo(userTwo);
    }

    @Override
    public List<Friend> getAllByUserOneAndFriendsSinceIsNull(User userOne) {
        if(userOne == null) log.info("getAllByUserOneAndFriendsSinceIsNull(): " + null);
        else                log.info("getAllByUserOneAndFriendsSinceIsNull(): " + userOne.toString());
        return friendsRepository.getAllByUserOneAndFriendsSinceIsNull(userOne);
    }

    @Override
    public List<Friend> getAllByUserTwoAndFriendsSinceIsNull(User userTwo) {
        if(userTwo == null) log.info("getAllByUserTwoAndFriendsSinceIsNull(): " + null);
        else                log.info("getAllByUserTwoAndFriendsSinceIsNull(): " + userTwo.toString());
        return friendsRepository.getAllByUserTwoAndFriendsSinceIsNull(userTwo);
    }

    @Override
    public Friend getByUserOneAndUserTwoAndFriendsSinceIsNotNull(User userOne, User userTwo) {
        StringBuilder sb = new StringBuilder();
        sb.append("getByUserOneAndUserTwoAndFriendsSinceIsNotNull(): userOne: ");
        if(userOne == null) sb.append("null");
        else                sb.append(userOne.toString());
        if(userTwo == null) sb.append(", userTwo: null");
        else                sb.append(", userTwo: ").append(userTwo.toString());
        log.info(sb.toString());
        return friendsRepository.getByUserOneAndUserTwoAndFriendsSinceIsNotNull(userOne, userTwo);
    }

    @Override
    public Friend getByUserOneAndUserTwoAndFriendsSinceIsNull(User userOne, User userTwo) {
        StringBuilder sb = new StringBuilder();
        sb.append("getByUserOneAndUserTwoAndFriendsSinceIsNull(): ");
        if(userOne == null) sb.append("userOne: null");
        else                sb.append("userOne: ").append(userOne.toString());
        if(userTwo == null) sb.append(", userTwo: null");
        else                sb.append(", userTwo: ").append(userTwo.toString());
        log.info(sb.toString());
        return friendsRepository.getByUserOneAndUserTwoAndFriendsSinceIsNull(userOne, userTwo);
    }

    @Override
    public boolean addFriend(Friend friend) {
        if(friend == null)  {
            log.info("addFriend(): " + null);
            return false;
        }
        else{
            log.info("addFriend(): " + friend.toString());
            friendsRepository.save(friend);
            return true;
        }
    }

    @Override
    public boolean removeFriend(Friend friend) {
        if(friend == null)  {
            log.info("removeFriend(): " + null);
            return false;
        }
        else{
            log.info("removeFriend(): " + friend.toString());
            friendsRepository.delete(friend);
            return true;
        }
    }
}

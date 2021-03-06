package com.carlmccann2.fakebookboot.controller;

import com.carlmccann2.fakebookboot.model.orm.Friend;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.services.FriendsService;
import com.carlmccann2.fakebookboot.model.services.UsersService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/friends")
public class FriendsController {

    private Log log = LogFactory.getLog(FriendsController.class);
    @Autowired
    private UsersService usersService;
    @Autowired
    private FriendsService friendsService;

    @GetMapping(value = "/{userId}")
    public Set<User> getAllByUser(@PathVariable Integer userId){
        log.info("getAllByUser(): " + userId);
        Set<User> friends = new HashSet<>();

        // TODO: may need to rethink using Set<User> rather than Set<Friend>
        for (Friend friend: friendsService.getAllByUserOne(usersService.getUser(userId))) {
            if(friend.getFriendsSince() != null){ // null value indicates request sent but not accepted
                friends.add(friend.getUserTwo());
            }
        }
        for(Friend friend: friendsService.getAllByUserTwo(usersService.getUser(userId))) {
            if(friend.getFriendsSince() != null){ // null value indicates request sent but not accepted
                friends.add(friend.getUserOne());
            }
        }
        return friends;
    }

    @GetMapping(value = "/requests/all")
    public Set<Friend> getFriendRequests(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        Set<Friend> friendRequests = new HashSet<>();
        friendRequests.addAll(friendsService.getAllByUserTwoAndFriendsSinceIsNull(user));
        StringBuilder sb = new StringBuilder();
        sb.append("getFriendRequests(): ");
        if(user == null)    sb.append("user: " + null);
        else                sb.append("user: " + user.toString());
        sb.append(", returns: [");
        friendRequests.forEach(friendRequest -> sb.append(friendRequest.toString() + ", "));
        sb.append("]");
        log.info(sb.toString());
        return friendRequests;
    }

    @PostMapping("/requests/{receiverUserId}")
    @Transactional
    public boolean sendFriendRequest(HttpServletRequest request, @PathVariable Integer receiverUserId){
        log.info("sendFriendRequest()");
        User user = (User) request.getSession().getAttribute("user");
        Friend friend = new Friend(user, usersService.getUser(receiverUserId),
                null, null);
        Friend f1 = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNotNull(friend.getUserOne(), friend.getUserTwo());
        Friend f2 = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNotNull(friend.getUserTwo(), friend.getUserOne());
        Friend f3 = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNull(friend.getUserOne(), friend.getUserTwo());
        Friend f4 = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNull(friend.getUserTwo(),friend.getUserOne());

        if(f1 == null && f2 == null && f3 == null && f4 == null){
            log.info("\tfrom user id " + user.getId() + " to " + receiverUserId);
            friendsService.addFriend(friend);
            return true;
        }
        else{
            log.info("\tuser_id: " + user.getId() + ", and user_id: " + receiverUserId + " already friends or request has been sent");
            return false;
        }

    }

    @PostMapping("/requests/accept")
    public void acceptFriendRequest(@RequestBody User senderUser, HttpServletRequest request){

        User receiverUser = (User) request.getSession().getAttribute("user");
        Friend friend = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNull(senderUser, receiverUser);
        friend.setFriendsSince(new Timestamp(System.currentTimeMillis()));
        friendsService.addFriend(friend);

        log.info("acceptFriendRequest(): sender: " + senderUser.toString() + ", receiver: " + receiverUser.toString());
    }

    @PostMapping("/requests/decline")
    public void declineFriendRequest(@RequestBody User senderUser, HttpServletRequest request){

        User receiverUser = (User) request.getSession().getAttribute("user");
        Friend friend = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNotNull(senderUser, receiverUser);
        friendsService.removeFriend(friend);
        log.info("declineFriendRequest(): sender: " + senderUser.toString() + ", receiver: " + receiverUser.toString());
    }

    @DeleteMapping("/delete")
    public void deleteFriend(@RequestBody User receiverUser, HttpServletRequest request){

        User senderUser = (User) request.getSession().getAttribute("user");
        Friend friend = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNotNull(senderUser, receiverUser);
        if(friend == null){
            friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNotNull(receiverUser, senderUser);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("deleteFriend(): ");
        if(senderUser == null) sb.append("senderUser: " + null);
        else                sb.append("senderUser: " + senderUser.toString());
        if(receiverUser == null) sb.append(", receiverUser: " + null);
        else                sb.append(", receiverUser: " + receiverUser.toString());
        log.info(sb.toString());
        friendsService.removeFriend(friend);
    }

    @GetMapping(value = "/arefriends/{userId}")
    public boolean areFriends(@PathVariable Integer userId, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        User otherUser = usersService.getUser(userId);
        if(user.getId().equals(otherUser.getId())){ // allows viewing of own profile even without friend object existing
            return true;
        }
        Friend friend = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNotNull(user, otherUser);
        log.info("areFriends(): user: " + user.toString() + ", otherUser: " + otherUser.toString());
        if(friend == null){
            friend = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNotNull(otherUser, user);
            if(friend == null){
                return false;
            }
        }
        log.info("\t" + friend.toString());

        return true;
    }

    @GetMapping(value = "/requestsent/{userId}")
    public String requestSent(@PathVariable Integer userId, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        User otherUser = usersService.getUser(userId);

        Friend friend = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNull(user, otherUser);
        log.info("requestSent(): user: " + user.toString() + ", otherUser: " + otherUser.toString());
        if(friend == null){

            friend = friendsService.getByUserOneAndUserTwoAndFriendsSinceIsNull(otherUser, user);
            if(friend == null){
                return "no request sent";
            }
            return "They sent request";
        }
        log.info("\t" + friend.toString());

        return "You sent request";
    }
}

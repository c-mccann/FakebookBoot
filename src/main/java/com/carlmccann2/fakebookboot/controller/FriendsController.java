package com.carlmccann2.fakebookboot.controller;

import com.carlmccann2.fakebookboot.model.orm.Friend;
import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.repositories.FriendsRepository;
import com.carlmccann2.fakebookboot.model.repositories.UsersRepository;
import com.carlmccann2.fakebookboot.model.services.FriendsService;
import com.carlmccann2.fakebookboot.model.services.UsersService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public boolean sendFriendRequest(@RequestBody HttpServletRequest request, @PathVariable Integer receiverUserId){
        log.info("sendFriendRequest()");
        User user = (User) request.getSession().getAttribute("user");
        Integer senderUserId = user.getId();
        Friend friend = new Friend(usersService.getUser(senderUserId), usersService.getUser(receiverUserId),
                null, null);
        if(friendsService.getByUserOneAndUserTwo(friend.getUserOne(), friend.getUserTwo()) == null){
            log.info("\tfrom user id " + user.getId() + " to " + receiverUserId);
            friendsService.addFriend(friend);
            return true;
        }
        else{
            log.info("\tfrom user id " + user.getId() + " to " + receiverUserId + " already exists");
            return false;
        }

    }

    @PostMapping("/requests/accept")
    public void acceptFriendRequest(@RequestBody User senderUser, HttpServletRequest request){

        User receiverUser = (User) request.getSession().getAttribute("user");
        log.info("t1: " + send)
        Friend friend = friendsService.getByUserOneAndUserTwo(senderUser, receiverUser);
        friend.setFriendsSince(new Timestamp(System.currentTimeMillis()));
        friendsService.addFriend(friend);

        log.info("acceptFriendRequest(): sender: " + senderUser.toString() + ", receiver: " + receiverUser.toString());
    }

    @PostMapping("/requests/decline")
    public void declineFriendRequest(@RequestBody User senderUser, HttpServletRequest request){

        User receiverUser = (User) request.getSession().getAttribute("user");
        Friend friend = friendsService.getByUserOneAndUserTwo(senderUser, receiverUser);
        friendsService.removeFriend(friend);
        log.info("declineFriendRequest(): sender: " + senderUser.toString() + ", receiver: " + receiverUser.toString());
    }

    @DeleteMapping("/delete")
    public void deleteFriend(@RequestBody User receiverUser, HttpServletRequest request){

        User senderUser = (User) request.getSession().getAttribute("user");
        Friend friend = friendsService.getByUserOneAndUserTwo(senderUser, receiverUser);
        if(friend == null){
            friendsService.getByUserOneAndUserTwo(receiverUser, senderUser);
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
        Friend friend = friendsService.getByUserOneAndUserTwo(user, otherUser);
        log.info("areFriends(): user: " + user.toString() + ", otherUser: " + otherUser.toString());
        if(friend == null){
            friend = friendsService.getByUserOneAndUserTwo(otherUser, user);
            if(friend == null){
                return false;
            }
        }
        log.info("\t" + friend.toString());

        return true;
    }
}

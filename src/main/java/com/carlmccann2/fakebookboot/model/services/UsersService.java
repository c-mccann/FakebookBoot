package com.carlmccann2.fakebookboot.model.services;

import com.carlmccann2.fakebookboot.model.orm.User;

import java.util.List;


public interface UsersService {

    User getUser(Integer id);

    User getUser(String email);

    List<User> getUsers();

    boolean addUser(User user);

    boolean registerUser(User user);

    void removeUser(Integer id);

    List<User> getUsersByNameNearestMatch(String fullName);

}

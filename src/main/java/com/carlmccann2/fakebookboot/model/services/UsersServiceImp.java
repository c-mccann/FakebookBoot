package com.carlmccann2.fakebookboot.model.services;

import com.carlmccann2.fakebookboot.model.orm.User;
import com.carlmccann2.fakebookboot.model.repositories.UsersRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service("usersService")
public class UsersServiceImp  implements UsersService {
    private Log log = LogFactory.getLog(UsersServiceImp.class);
    @Autowired
    private UsersRepository usersRepository;

    @Override
    @Transactional
    public User getUser(Integer id) {
        log.info("getUser(): " + id);
        return usersRepository.findOne(id);
    }

    @Override
    @Transactional
    public User getUser(String email) {
        return usersRepository.getUserByEmail(email);
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return usersRepository.findAll();
    }

    @Override
    @Transactional
    public boolean addUser(User user) {
        log.info("addUser(): " + user.getEmail());
        User existingUser = usersRepository.getUserByEmail(user.getEmail());
        if(existingUser == null){
            usersRepository.save(user);
            log.info("\tsuccess");
        }
        else{
            log.info("\terror: user exists already");
            return false;
        }
        return true;

    }
    @Override
    @Transactional
    public boolean registerUser(User user) {
        log.info("registerUser(): " + user.getEmail());
        User existingUser = usersRepository.getUserByEmail(user.getEmail());
        if (existingUser != null) {
            if (existingUser.getAccountCreated() == null){
                log.info("\tsuccess: activated account " + user.getId());
                existingUser.setAccountCreated(new Timestamp(System.currentTimeMillis()));
                usersRepository.save(existingUser);
                return true;
            }
        }
        return false;
    }



    @Override
    @Transactional
    public void removeUser(Integer id){
        usersRepository.delete(id);
    }

    @Override
    @Transactional
    public List<User> getUsersByNameNearestMatch(String fullName){
        return usersRepository.getUsersByNameNearestMatch(fullName);
    }
}

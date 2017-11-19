package com.carlmccann2.fakebookboot.model.services;

import com.carlmccann2.fakebookboot.model.orm.User;

public interface MailService {

    public void sendEmailOnUserRegistration(User user);

}

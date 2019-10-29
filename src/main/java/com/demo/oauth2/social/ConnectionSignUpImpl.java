package com.demo.oauth2.social;

import com.demo.oauth2.dao.AppUserDAO;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

public class ConnectionSignUpImpl implements ConnectionSignUp {

    public ConnectionSignUpImpl(AppUserDAO appUserDAO) {
    }

    // After logging in social networking.
    // This method will be called to create a corresponding App_User record
    // if it does not already exist.
    @Override
    public String execute(Connection<?> connection) {
//        AppUser account = userDetailsServiceImpl.createAppUser(connection);
//        return account.getUserName();
        return "nothing";
    }

}
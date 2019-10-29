package com.demo.oauth2.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.demo.oauth2.entity.AppRole;
import com.demo.oauth2.entity.AppUser;
import com.demo.oauth2.form.AppUserForm;
import com.demo.oauth2.utils.EncrytedPasswordUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AppUserDAO {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AppRoleDAO appRoleDAO;

    @Autowired
    private UserRepository userRepository;

    public AppUser findAppUserByUserId(Long userId) {
        try {
            AppUser appUser = userRepository.findByUserId(userId);
            return appUser;
        } catch (NoResultException e) {
            return null;
        }
    }

    public AppUser findAppUserByUserName(String userName) {
        try {
            AppUser appUser = userRepository.findByUserName(userName);
            return appUser;
        } catch (NoResultException e) {
            return null;
        }
    }

    public AppUser findByEmail(String email) {
        try {
            AppUser appUser = userRepository.findByEmail(email);
            return appUser;
        } catch (NoResultException e) {
            return null;
        }
    }

    public String getUserRole(Long userId) {
        try {
            AppUser appUser = userRepository.findRoleByUserId(userId);
            return appUser.getRoles().getRoleName();
        } catch (NoResultException e) {
            return null;
        }
    }

    public String findAvailableUserName(String userName_prefix) {
        AppUser account = this.findAppUserByUserName(userName_prefix);
        if (account == null) {
            return userName_prefix;
        }
        int i = 0;
        while (true) {
            String userName = userName_prefix + "_" + i++;
            account = this.findAppUserByUserName(userName);
            if (account == null) {
                return userName;
            }
        }
    }

}

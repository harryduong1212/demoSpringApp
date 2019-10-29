package com.demo.oauth2.service;

import com.demo.oauth2.form.AppUserForm;
import com.demo.oauth2.entity.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor"
)

@Component
public class UserMapperImpl implements UserMapper {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMapperImpl.class);

    @Override
    public AppUserForm toUserForm(AppUser appUser) {
        if(appUser == null) { return null; }

        AppUserForm.AppUserFormBuilder appUserForm = AppUserForm.builder();

        appUserForm.userName(appUser.getUserName());
        appUserForm.userId(appUser.getUserId());
        appUserForm.email(appUser.getEmail());
        appUserForm.enable(appUser.isEnabled());
        appUserForm.role(appUser.getRoles().getRoleName());
        appUserForm.update(appUser.getLastModifiedDate());
        appUserForm.create(appUser.getCreatedDate());
        appUserForm.firstName(appUser.getFirstName());
        appUserForm.lastName(appUser.getLastName());
        appUserForm.role(appUser.getRoles().getRoleName());
        LOGGER.info("Convert appUser to appUserForm successfully!");
        return appUserForm.build();
    }

    @Override
    public AppUser toAppUser(AppUserForm appUserForm) {
        if(appUserForm == null) { return null; }

        AppUser.AppUserBuilder appUser = AppUser.builder();

        appUser.userName(appUserForm.getUserName());
        appUser.encrytedPassword(bCryptPasswordEncoder.encode(appUserForm.getPassword()));
        appUser.email(appUserForm.getEmail());
        LOGGER.info("Convert appUserForm to appUser successfully!");
        return appUser.build();
    }

    @Override
    public List<AppUserForm> toAppUserForm(List<AppUser> appUser) {
        if(appUser == null) { return null; }
        List<AppUserForm> appUserForms = new ArrayList<>();
        for (AppUser user : appUser) {
            AppUserForm.AppUserFormBuilder appUserForm = AppUserForm.builder();
            appUserForm.userName(user.getUserName());
            appUserForm.userId(user.getUserId());
            appUserForm.email(user.getEmail());
            appUserForm.enable(user.isEnabled());
            appUserForm.role(user.getRoles().getRoleName());
            appUserForm.update(user.getLastModifiedDate());
            appUserForm.create(user.getCreatedDate());
            appUserForms.add(appUserForm.build());
        }
        return appUserForms;
    }
}

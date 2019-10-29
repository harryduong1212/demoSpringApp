package com.demo.oauth2.service;

import com.demo.oauth2.form.AppUserForm;
import com.demo.oauth2.entity.AppUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper //Creates a Spring Bean automatically
interface UserMapper {

    AppUserForm toUserForm(AppUser appUser);
    AppUser toAppUser(AppUserForm appUserForm);
    List<AppUserForm> toAppUserForm(List<AppUser> appUser);
}
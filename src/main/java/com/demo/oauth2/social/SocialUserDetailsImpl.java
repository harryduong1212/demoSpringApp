package com.demo.oauth2.social;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.demo.oauth2.entity.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;

public class SocialUserDetailsImpl implements SocialUserDetails {

    private static final long serialVersionUID = -5246117266247684905L;

    private List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

    private AppUser appUser;

    public SocialUserDetailsImpl(AppUser appUser, String roleNames) {
        this.appUser = appUser;

        GrantedAuthority authority = new SimpleGrantedAuthority(roleNames);
        list.add(authority);

        UserDetails userDetails = (UserDetails) new User(appUser.getUserName(), //
                appUser.getEncrytedPassword(), list);
    }

    @Override
    public String getUserId() {
        return this.appUser.getUserId() + "";
    }

    @Override
    public String getUsername() {
        return appUser.getUserName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return list;
    }

    @Override
    public String getPassword() {
        return appUser.getEncrytedPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
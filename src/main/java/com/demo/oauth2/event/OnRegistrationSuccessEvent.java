package com.demo.oauth2.event;

import com.demo.oauth2.entity.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationSuccessEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private String appUrl;
    private Locale locale;
    private AppUser appUser;
    private String password;

    public OnRegistrationSuccessEvent(AppUser appUser, Locale locale, String appUrl, String password) {
        super(appUser);
        this.appUser = appUser;
        this.locale = locale;
        this.appUrl = appUrl;
        this.password = password;
    }

}
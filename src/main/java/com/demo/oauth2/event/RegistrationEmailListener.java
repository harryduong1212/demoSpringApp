package com.demo.oauth2.event;

import com.demo.oauth2.entity.AppUser;
import com.demo.oauth2.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;


import java.util.UUID;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MailSender mailSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationEmailListener.class);

    @Override
    public void onApplicationEvent(OnRegistrationSuccessEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationSuccessEvent event) {
        AppUser appUser = event.getAppUser();

        if(event.getPassword().compareTo("") == 0) {
            String token = UUID.randomUUID().toString();
            userDetailsServiceImpl.createVerificationToken(appUser, token);
            String recipient = appUser.getEmail();
            String subject = "Registration Confirmation";
            String url = event.getAppUrl() + "/confirmRegistration?token=" + token;
            String message = messageSource.getMessage("message.registrationSuccessEmail", null, event.getLocale());
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(recipient);
            email.setSubject(subject);
            email.setText(message + "http://localhost:8080" + url);
            System.out.println(url);
            mailSender.send(email);
            LOGGER.info("Verification token of user " + appUser.getUserName() + " has been sent!");
        }
        else {
            String recipient = appUser.getEmail();
            String subject = "Registration Confirmation";
            String message = messageSource.getMessage("message.registrationSuccessEmailPassword", null, event.getLocale());
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(recipient);
            email.setSubject(subject);
            email.setText(message + ": " + event.getPassword());
            mailSender.send(email);
            LOGGER.info("Password of user " + appUser.getUserName() + " has been sent!");
        }
    }
}
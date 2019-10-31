package com.demo.oauth2.config;

import com.demo.oauth2.dao.UserRepository;
import com.demo.oauth2.entity.AppUser;
import com.demo.oauth2.form.AppUserForm;
import com.demo.oauth2.service.UserDetailsServiceImpl;
import com.demo.oauth2.service.UserMapperImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CronScheduled {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private UserMapperImpl userMapperImpl;

    private static final Logger LOGGER = LoggerFactory.getLogger(CronScheduled.class);

    @Async
    @Scheduled(cron = "0/10 * * * * MON-THU")
    public void collectAllUser() throws JsonProcessingException {
        List<AppUser> appUserList = userRepository.findAll();
        for( AppUser x : appUserList) {
            AppUserForm temp;
            temp = userMapperImpl.toUserForm(x);
            String serialize = userDetailsServiceImpl.testSerialize(temp);
            System.out.println( serialize );
            AppUserForm appUserForm = userDetailsServiceImpl.testDeserialize(serialize);
            System.out.println( appUserForm.toFormString() );
            //LOGGER.info(x.getUserName());
        }
    }

}

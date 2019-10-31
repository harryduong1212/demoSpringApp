package com.demo.oauth2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import com.demo.oauth2.dao.AppUserDAO;
import com.demo.oauth2.dao.TokenRepository;
import com.demo.oauth2.dao.UserRepository;
import com.demo.oauth2.entity.AppRole;
import com.demo.oauth2.entity.AppUser;
import com.demo.oauth2.entity.VerificationToken;
import com.demo.oauth2.event.OnRegistrationSuccessEvent;
import com.demo.oauth2.form.AppUserForm;
import com.demo.oauth2.form.SearchForm;
import com.demo.oauth2.json.CustomUserDeserializer;
import com.demo.oauth2.json.CustomUserSerialize;
import com.demo.oauth2.social.SocialUserDetailsImpl;
import com.demo.oauth2.utils.EncrytedPasswordUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.*;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.NumberUtils;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private UserMapperImpl userMapperImpl;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private UsersConnectionRepository connectionRepository;

    @Autowired
    CacheManager cacheManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        System.out.println("UserDetailsServiceImpl.loadUserByUsername=" + userName);

        AppUser appUser = this.appUserDAO.findAppUserByUserName(userName);
        LOGGER.info("User "+ userName + " has been found!");
        if (appUser == null) {
            System.out.println("User not found! " + userName);
            LOGGER.error("User "+ userName + " was not found in the database!");
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        System.out.println("Found User: " + appUser);

        // [ROLE_USER, ROLE_ADMIN,..]
        String roleNames = this.appUserDAO.getUserRole(appUser.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority(roleNames);
        grantList.add(authority);

        SocialUserDetailsImpl userDetails = new SocialUserDetailsImpl(appUser, roleNames);


        return userDetails;
    }


    public void createRoleFor(AppUser appUser, String roleNames) {
        //
        AppRole appRole = new AppRole(2L, "ROLE_ADMIN");
        appUser.setRoles(appRole);
        userRepository.save(appUser);
    }

    // Auto create App User Account.
    public AppUser createAppUser(Connection<?> connection, WebRequest request) {
        ProviderSignInUtils providerSignInUtils //
                = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
        ConnectionKey key = connection.getKey();
        // (facebook,12345), (google,123) ...

        System.out.println("key= (" + key.getProviderId() + "," + key.getProviderUserId() + ")");

        UserProfile socialUserProfile = connection.fetchUserProfile();

        String email = socialUserProfile.getEmail();
        AppUser appUser = appUserDAO.findByEmail(email);
        if (appUser != null) {
            providerSignInUtils.doPostSignUp(appUser.getUserName(), request);
            LOGGER.info("User " + appUser.getUserName() + " has been found! Login through " + key.getProviderId());
            return appUser;
        }

        String randomPassword = UUID.randomUUID().toString().substring(0, 8);
        String encrytedPassword = EncrytedPasswordUtils.encrytePassword(randomPassword);

        appUser = new AppUser();
        appUser.setEnabled(true);
        appUser.setEncrytedPassword(encrytedPassword);
        appUser.setUserName(email);
        appUser.setEmail(email);
        appUser.setFirstName(socialUserProfile.getFirstName());
        appUser.setLastName(socialUserProfile.getLastName());

        this.entityManager.persist(appUser);

        // Create default Role
        this.createRoleFor(appUser, "");

        Locale locale = Locale.ENGLISH;
        eventPublisher.publishEvent(new OnRegistrationSuccessEvent(appUser, locale, "", randomPassword));
        providerSignInUtils.doPostSignUp(appUser.getUserName(), request);

        LOGGER.info("User " + appUser.getUserName() + " has been created through social!");

        return appUser;
    }

    public AppUser registerNewUserAccount(AppUserForm appUserForm, String roleNames, WebRequest request) {
        AppUser appUser = new AppUser();
        appUser.setUserName(appUserForm.getUserName());
        appUser.setEmail(appUserForm.getEmail());
        appUser.setFirstName(appUserForm.getFirstName());
        appUser.setLastName(appUserForm.getLastName());
        appUser.setEnabled(false);
        String encrytedPassword = EncrytedPasswordUtils.encrytePassword(appUserForm.getPassword());
        appUser.setEncrytedPassword(encrytedPassword);
        this.entityManager.persist(appUser);
        this.entityManager.flush();
        this.createRoleFor(appUser, roleNames);
        LOGGER.info("User " + appUser.getUserName() + " has been created!");


        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationSuccessEvent(appUser, request.getLocale(),appUrl, ""));

        return appUser;
    }

    public void createVerificationToken(AppUser appUser, String token) {
        VerificationToken newUserToken = new VerificationToken(token, appUser);
        LOGGER.info("Verification token of user " + appUser.getUserName() + " has been created!");
        tokenRepository.save(newUserToken);
    }

    @Transactional
    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }

    @Transactional
    public void enableRegisteredUser(AppUser appUser) {
        appUser.setEnabled(true);
        LOGGER.info("User " + appUser.getUserName() + " is actived!");
        userRepository.save(appUser);
    }

    private void simulateSlowService() {
        try {
            Thread.sleep(3000L);
            System.out.println("System sleep");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Cacheable(value="serialize", keyGenerator="customKeyGenerator")
    public String testSerialize(AppUserForm appUserForm) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(AppUserForm.class, new CustomUserSerialize());
        mapper.registerModule(module);


        String serialized = mapper.writeValueAsString(appUserForm);
        simulateSlowService();
        return serialized;
    }

    @Cacheable(value="deserialize", keyGenerator="customKeyGenerator")
    public AppUserForm testDeserialize(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(AppUserForm.class, new CustomUserDeserializer());
        mapper.registerModule(module);

        AppUserForm appUserForm = mapper.readValue(json, AppUserForm.class);
        simulateSlowService();
        return appUserForm;
    }

    public List<AppUserForm> getAllUsers() {
        List<AppUser> appUserList = userRepository.findAll();
        return userMapperImpl.toAppUserForm(appUserList);
    }

    public List<AppUser> testSearchSpecification(String searchKey) {
        SearchForm searchForm = new SearchForm();
        List<AppUser> appUserList = new ArrayList<>();
        List<AppUser> resultList = new ArrayList<>();
        for(String x : searchForm.getSearchRange()) {
            try {
                Long.parseLong(searchKey);
                if(x.compareTo("email") != 0 && x.compareTo("userName") != 0) {
                    appUserList = userRepository.findAll(UserSearchSpecification.getUsersByUserIdSpec(x, Long.parseLong(searchKey)));
                }
            } catch (Exception e) {
                if(x.compareTo("userId") != 0) {
                    appUserList = userRepository.findAll(UserSearchSpecification.getUsersByNameSpec(x, searchKey));
                }
            }
            resultList.addAll(appUserList);
        }
        //List<AppUser> appUserList = userRepository.findAll(UserSearchSpecification.getUsersByNameSpec(searchForm.getSearchRange().get(2), searchKey));
        return resultList;
    }
}


package com.demo.oauth2.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserForm {
    @JsonProperty
    private Long userId;
    @JsonProperty
    private String email;
    @JsonProperty
    private String userName;
    @JsonProperty
    private boolean enable;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonIgnore
    private String password;
    @JsonProperty
    private String role;
    @JsonProperty
    private String signInProvider;
    @JsonProperty
    private String providerUserId;
    @JsonProperty
    private Date update;
    @JsonProperty
    private Date create;


    public AppUserForm(Connection<?> connection) {
        UserProfile socialUserProfile = connection.fetchUserProfile();
        this.userId = null;
        this.email = socialUserProfile.getEmail();
        this.userName = socialUserProfile.getUsername();
        this.firstName = socialUserProfile.getFirstName();
        this.lastName = socialUserProfile.getLastName();

        ConnectionKey key = connection.getKey();
        // google, facebook, twitter
        this.signInProvider = key.getProviderId();

        // ID of User on google, facebook, twitter.
        // ID của User trên google, facebook, twitter.
        this.providerUserId = key.getProviderUserId();
    }

    public String toFormString() {
        return "UserId: " + this.userId + " \n" +
                "Username: " + this.userName + " \n" +
                "Email: " + this.email + " \n" +
                "Enable: " + this.enable + " \n" +
                "Firstname: " + this.firstName + " \n" +
                "Lastname: " + this.lastName + " \n" +
                "Role: " + this.role + " \n" +
                "Update: " + this.update.toString() + " \n" +
                "Create: " + this.create.toString() + " \n";
    }
}
package com.demo.oauth2.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "Userconnection")
public class UserConnection implements Serializable {

    private static final long serialVersionUID = -6991752510891411572L;

    @Id
    @Column(name = "Userid", length = 255, nullable = false)
    private String userId;

    @Id
    @Column(name = "Providerid", length = 255, nullable = false)
    private String providerId;

    @Id
    @Column(name = "Provideruserid", length = 255, nullable = false)
    private String providerUserId;

    @Column(name = "Rank", nullable = false)
    private int rank;

    @Column(name = "Displayname", length = 255, nullable = true)
    private String displayName;

    @Column(name = "Profileurl", length = 512, nullable = true)
    private String profileUrl;

    @Column(name = "Imageurl", length = 512, nullable = true)
    private String imageUrl;

    @Column(name = "Accesstoken", length = 512, nullable = true)
    private String accessToken;

    @Column(name = "Secret", length = 512, nullable = true)
    private String secret;

    @Column(name = "Refreshtoken", length = 512, nullable = true)
    private String refreshToken;

    @Column(name = "Expiretime", nullable = true)
    private Long expireTime;

}
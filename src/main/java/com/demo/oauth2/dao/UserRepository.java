package com.demo.oauth2.dao;

import com.demo.oauth2.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long>,
        JpaSpecificationExecutor<AppUser> {
    AppUser findByUserName(String userName);

    AppUser findByUserId(Long userId);

    AppUser findByEmail(String email);

    AppUser findRoleByUserId(Long userId);
}
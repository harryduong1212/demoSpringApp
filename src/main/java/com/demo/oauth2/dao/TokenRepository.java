package com.demo.oauth2.dao;

import com.demo.oauth2.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String verificationToken);

}

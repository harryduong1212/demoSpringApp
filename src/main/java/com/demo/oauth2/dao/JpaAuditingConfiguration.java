package com.demo.oauth2.dao;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class JpaAuditingConfiguration implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        /*
          if you are using spring security, you can get the currently logged username with following code segment.
          SecurityContextHolder.getContext().getAuthentication().getName()
         */
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
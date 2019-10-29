package com.demo.oauth2.dao;

import com.demo.oauth2.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByRoleName(String roleName);


}
package com.demo.oauth2.dao;

import java.util.List;

import javax.management.relation.Role;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.demo.oauth2.entity.AppRole;
import com.demo.oauth2.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AppRoleDAO {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    public AppRole findAppRoleByName(String roleName) {
        try {
            AppRole appRole = roleRepository.findByRoleName(roleName);
            return appRole;
        } catch (NoResultException e) {
            return null;
        }
    }

}
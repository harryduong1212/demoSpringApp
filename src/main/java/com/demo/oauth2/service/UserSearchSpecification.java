package com.demo.oauth2.service;

import com.demo.oauth2.entity.AppUser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.*;

public class UserSearchSpecification {

    public static Specification<AppUser> getUsersByNameSpec(String userName, String searchKey) {
        return new Specification<AppUser>() {
            @Override
            public Predicate toPredicate(Root<AppUser> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(userName), searchKey);
            }
        };
    }

    public static Specification<AppUser> getUsersByEmailSpec(String email, String searchKey) {
        return (Specification<AppUser>) (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(email), searchKey);
        };
    }

    public static Specification<AppUser> getUsersByUserIdSpec(String userId, Long searchKey) {
        return (Specification<AppUser>) (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(userId), searchKey);
        };
    }
}

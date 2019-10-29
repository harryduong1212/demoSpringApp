package com.demo.oauth2.entity;

import lombok.*;
import com.demo.oauth2.event.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "App_User", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "APP_USER_UK", columnNames = "User_Name"),
                @UniqueConstraint(name = "APP_USER_UK2", columnNames = "Email") })
public class AppUser extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id", nullable = false)
    private Long userId;

    @Column(name = "User_Name", length = 36, nullable = false)
    private String userName;

    @Column(name = "Email", length = 128, nullable = false)
    private String email;

    @Column(name = "First_Name", length = 36, nullable = true)
    private String firstName;

    @Column(name = "Last_Name", length = 36, nullable = true)
    private String lastName;

    @Column(name = "Encryted_Password", length = 128, nullable = false)
    private String encrytedPassword;

    @Column(name = "Enabled", length = 1, nullable = false)
    private boolean enabled;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinTable(name = "User_Role",
            joinColumns = { @JoinColumn(name = "User_Id") },
            inverseJoinColumns = { @JoinColumn(name = "Role_Id") })
    private AppRole roles;
}
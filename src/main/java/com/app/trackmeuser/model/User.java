package com.app.trackmeuser.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USER_TABLE")
public class User extends BaseTime {

    @Id
    @Column(name = "USER_ID")
    private String username;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Column
    private UserRoleType role;

}


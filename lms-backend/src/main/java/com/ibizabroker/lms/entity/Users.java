package com.ibizabroker.lms.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String username;
    private String name;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<Role> role;

    public Users(String username, String name, String password, Set<Role> role) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Users() {
    }
}


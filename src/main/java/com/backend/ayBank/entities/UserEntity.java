package com.backend.ayBank.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity(name="users")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false,length = 50)
    private String firstName;
    @Column(nullable = true)
    private Boolean admin;
    @Column(nullable = false,length = 50)
    private String lastName;
    @Column(nullable = false,length = 100,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = true)
    private String imgProfile;
    @Column(nullable = true)
    private String encryptedPassword;
    @Column(nullable = true)
    private String emailVerificationToken;
    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<CreditEntity> credits;


}

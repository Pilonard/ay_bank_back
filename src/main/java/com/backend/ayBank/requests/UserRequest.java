package com.backend.ayBank.requests;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserRequest implements Serializable {

    private String firstName;
    private String lastName;
    private Boolean admin;
    private String email;
    private String password;
    private String imgProfile;
    private List<CreditRequest> credits;
}

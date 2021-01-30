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
    private String email;
    private String password;
    private List<CreditRequest> credits;
}

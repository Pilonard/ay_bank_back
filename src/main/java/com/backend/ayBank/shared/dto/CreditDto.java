package com.backend.ayBank.shared.dto;

import lombok.*;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CreditDto {
    private long id;
    private String idCredit;
    private Double annuity;
    private Double capital;
    private String typeCredit;
    private Long duration;
    private String imgProfile;
    private Double salary;

    private UserDto user;


}

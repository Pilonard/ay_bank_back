package com.backend.ayBank.shared.dto;

import lombok.*;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CreditDto {
    private long id;
    private String idCredit;
    private Double annuity;
    private Double capital;
    private String typeCredit = "being processed";
    private Long duration;
    private Double salary;
    private String creditState;
    private UserDto user;


}

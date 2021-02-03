package com.backend.ayBank.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditResponse {
    private String idCredit;
    private Double annuity;
    private Double capital;
    private String typeCredit;
    private Long duration;
    private Double salary;
    private String creditState = "being pr";
}

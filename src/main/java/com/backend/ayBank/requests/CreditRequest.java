package com.backend.ayBank.requests;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CreditRequest {

    private String idCredit;
    private Double annuity;
    private Double capital;
    private String typeCredit;
    private Long duration;
    private Double salary;
    private String creditState;

}

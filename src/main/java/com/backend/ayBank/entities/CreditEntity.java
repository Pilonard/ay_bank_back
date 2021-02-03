package com.backend.ayBank.entities;

import com.backend.ayBank.shared.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity(name="credit")
public class CreditEntity {
    @Id
    @GeneratedValue
    private long id;
    private String idCredit;
    private Double annuity;
    private Double capital;
    private String typeCredit;
    private Long duration;
    private Double salary;
    @Column( columnDefinition = "varchar(255) default 'being processed'")
    private String creditState;
    @JoinColumn(name= "users_id",referencedColumnName = "id")
    @ManyToOne
    private UserEntity user;
}

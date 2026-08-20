package com.statemachine.sm.domain;

import com.statemachine.sm.service.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String iban;
    private String type;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
}

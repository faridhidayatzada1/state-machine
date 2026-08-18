package com.java.user.statemachine.sm.transaction;

import com.java.user.statemachine.sm.AccountStatus;
import lombok.Data;

@Data
public class AccountDto {

    private Long id;
    private String name;
    private AccountStatus status;
}

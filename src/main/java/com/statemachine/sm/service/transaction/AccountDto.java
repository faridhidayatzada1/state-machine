package com.statemachine.sm.service.transaction;

import com.statemachine.sm.service.AccountStatus;
import lombok.Data;

@Data
public class AccountDto {

    private Long id;
    private String name;
    private AccountStatus status;
}

package com.ismaildrs.demo_cqrs_es_axon.events;

import com.ismaildrs.demo_cqrs_es_axon.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountCreatedEvent {
    private String accountId;
    private AccountStatus accountStatus;
    private double initialBalance;
    private String currency;
}

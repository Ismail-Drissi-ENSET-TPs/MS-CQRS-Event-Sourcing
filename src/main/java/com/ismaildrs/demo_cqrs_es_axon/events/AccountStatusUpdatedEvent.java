package com.ismaildrs.demo_cqrs_es_axon.events;

import com.ismaildrs.demo_cqrs_es_axon.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class AccountStatusUpdatedEvent {
    private String accountId;
    private AccountStatus accountStatus;
}

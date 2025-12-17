package com.ismaildrs.demo_cqrs_es_axon.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountDebitedEvent {
    private String accountId;
    private double amount;
    private String currency;
}

package com.ismaildrs.demo_cqrs_es_axon.commands.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreditAccountCommand {
    @TargetAggregateIdentifier
    private String id;
    private double amount;
    private String currency;
}

package com.ismaildrs.demo_cqrs_es_axon.commands.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DebitAccountCommand {
    @TargetAggregateIdentifier
    private String id;
    private double amount;
    private String currency;
}

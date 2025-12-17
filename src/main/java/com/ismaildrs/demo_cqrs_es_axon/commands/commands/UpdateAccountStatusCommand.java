package com.ismaildrs.demo_cqrs_es_axon.commands.commands;

import com.ismaildrs.demo_cqrs_es_axon.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Getter
public class UpdateAccountStatusCommand {
    @TargetAggregateIdentifier
    private String id;
    private AccountStatus accountStatus;
}

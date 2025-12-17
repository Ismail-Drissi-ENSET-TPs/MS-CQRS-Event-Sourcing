package com.ismaildrs.demo_cqrs_es_axon.commands.aggregates;

import com.ismaildrs.demo_cqrs_es_axon.commands.commands.AddAccountCommand;
import com.ismaildrs.demo_cqrs_es_axon.commands.commands.CreditAccountCommand;
import com.ismaildrs.demo_cqrs_es_axon.commands.commands.DebitAccountCommand;
import com.ismaildrs.demo_cqrs_es_axon.commands.commands.UpdateAccountStatusCommand;
import com.ismaildrs.demo_cqrs_es_axon.enums.AccountStatus;
import com.ismaildrs.demo_cqrs_es_axon.events.AccountCreatedEvent;
import com.ismaildrs.demo_cqrs_es_axon.events.AccountCreditedEvent;
import com.ismaildrs.demo_cqrs_es_axon.events.AccountDebitedEvent;
import com.ismaildrs.demo_cqrs_es_axon.events.AccountStatusUpdatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
@Slf4j
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private double balance;
    private AccountStatus status;

    // Creation command
    @CommandHandler
    public AccountAggregate(AddAccountCommand cmd) {
        if (cmd.getInitialBalance() <= 0) throw new IllegalArgumentException("Initial balance must be positive");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                cmd.getId(),
                AccountStatus.CREATED,
                cmd.getInitialBalance(),
                cmd.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getAccountStatus();
    }

    // Credit command
    @CommandHandler
    public void handle(CreditAccountCommand cmd) {
        if (!AccountStatus.ACTIVATED.equals(status)) throw new RuntimeException("Account is not activated");
        if (cmd.getAmount() <= 0) throw new IllegalArgumentException("Amount must be positive");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                cmd.getId(),
                cmd.getAmount(),
                cmd.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        this.balance += event.getAmount();
    }

    // Debit command
    @CommandHandler
    public void handle(DebitAccountCommand cmd) {
        if (!AccountStatus.ACTIVATED.equals(status)) throw new RuntimeException("Account is not activated");
        if (balance < cmd.getAmount()) throw new RuntimeException("Amount exceeds balance");
        if (cmd.getAmount() <= 0) throw new IllegalArgumentException("Amount must be positive");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                cmd.getId(),
                cmd.getAmount(),
                cmd.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        this.balance -= event.getAmount();
    }

    @CommandHandler
    public void handle(UpdateAccountStatusCommand cmd) {
        if (status == cmd.getAccountStatus()) throw new RuntimeException("Status is already the same");
        AggregateLifecycle.apply(new AccountStatusUpdatedEvent(
                cmd.getId(),
                cmd.getAccountStatus()
        ));
    }

    @EventSourcingHandler
    public void on(AccountStatusUpdatedEvent event) {
        this.status = event.getAccountStatus();
    }
}

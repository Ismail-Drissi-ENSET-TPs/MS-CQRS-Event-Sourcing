package com.ismaildrs.demo_cqrs_es_axon.query.handlers;

import com.ismaildrs.demo_cqrs_es_axon.enums.OperationType;
import com.ismaildrs.demo_cqrs_es_axon.events.AccountCreatedEvent;
import com.ismaildrs.demo_cqrs_es_axon.events.AccountCreditedEvent;
import com.ismaildrs.demo_cqrs_es_axon.events.AccountDebitedEvent;
import com.ismaildrs.demo_cqrs_es_axon.events.AccountStatusUpdatedEvent;
import com.ismaildrs.demo_cqrs_es_axon.query.entities.Account;
import com.ismaildrs.demo_cqrs_es_axon.query.entities.AccountOperation;
import com.ismaildrs.demo_cqrs_es_axon.query.repositories.AccountRepository;
import com.ismaildrs.demo_cqrs_es_axon.query.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AccountEventHandler {

    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private QueryUpdateEmitter queryUpdateEmitter;

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage eventMessage){
        Account account = Account.builder()
                .id(event.getAccountId())
                .balance(event.getInitialBalance())
                .accountStatus(event.getAccountStatus())
                .createdAt(eventMessage.getTimestamp())
                .currency(event.getCurrency())
                .build();

        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountStatusUpdatedEvent event){
        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setAccountStatus(event.getAccountStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event, EventMessage eventMessage){
        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .account(account)
                        .date(eventMessage.getTimestamp())
                                .type(OperationType.DEBIT)
                                        .currency(event.getCurrency())
                                                .build();

        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance() - accountOperation.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(e->true, accountOperation);
    }

    @EventHandler
    public void on(AccountCreditedEvent event, EventMessage eventMessage){
        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .account(account)
                .date(eventMessage.getTimestamp())
                .type(OperationType.CREDIT)
                .currency(event.getCurrency())
                .build();

        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance() + accountOperation.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(e->true, accountOperation);

    }



}

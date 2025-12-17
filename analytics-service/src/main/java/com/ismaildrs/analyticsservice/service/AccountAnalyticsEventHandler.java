package com.ismaildrs.analyticsservice.service;

import com.ismaildrs.analyticsservice.entities.AccountAnalytics;
import com.ismaildrs.analyticsservice.queries.GetAccountAnalyticsByAccountIdQuery;
import com.ismaildrs.analyticsservice.repo.AccountAnalyticsRepository;
import com.ismaildrs.demo_cqrs_es_axon.events.AccountCreatedEvent;
import com.ismaildrs.demo_cqrs_es_axon.events.AccountCreditedEvent;
import com.ismaildrs.demo_cqrs_es_axon.events.AccountDebitedEvent;
import com.ismaildrs.demo_cqrs_es_axon.query.entities.Account;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountAnalyticsEventHandler {
    private AccountAnalyticsRepository accountAnalyticsRepository;
    private QueryUpdateEmitter queryUpdateEmitter;

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        log.info("AccountCreatedEvent received for {}", accountCreatedEvent);
        AccountAnalytics accountAnalytics = AccountAnalytics.builder()
                .accountId(accountCreatedEvent.getAccountId())
                .totalCredit(0)
                .totalDebit(0)
                .balance(accountCreatedEvent.getInitialBalance())
                .totalNumberOfCredits(0)
                .totalNumberOfDebits(0)
                .build();
        accountAnalyticsRepository.save(accountAnalytics);
        queryUpdateEmitter.emit(GetAccountAnalyticsByAccountIdQuery.class, (query)->query.getAccountId().equals(accountAnalytics.getAccountId()), accountAnalytics);
    }

    @EventHandler
    public void on(AccountDebitedEvent  accountDebitedEvent) {
        AccountAnalytics accountAnalytics = accountAnalyticsRepository.findByAccountId(accountDebitedEvent.getAccountId());
        accountAnalytics.setBalance(accountAnalytics.getBalance() - accountDebitedEvent.getAmount());
        accountAnalytics.setTotalDebit(accountAnalytics.getTotalCredit() + accountDebitedEvent.getAmount());
        accountAnalytics.setTotalNumberOfDebits(accountAnalytics.getTotalNumberOfDebits()+1);
        accountAnalyticsRepository.save(accountAnalytics);
        queryUpdateEmitter.emit(GetAccountAnalyticsByAccountIdQuery.class, (query)->query.getAccountId().equals(accountAnalytics.getAccountId()), accountAnalytics);
    }

    @EventHandler
    public void on(AccountCreditedEvent  accountCreditedEvent) {
        AccountAnalytics accountAnalytics = accountAnalyticsRepository.findByAccountId(accountCreditedEvent.getAccountId());
        accountAnalytics.setBalance(accountAnalytics.getBalance() + accountCreditedEvent.getAmount());
        accountAnalytics.setTotalCredit(accountAnalytics.getTotalCredit() + accountCreditedEvent.getAmount());
        accountAnalytics.setTotalNumberOfCredits(accountAnalytics.getTotalNumberOfDebits()+1);
        accountAnalyticsRepository.save(accountAnalytics);
        queryUpdateEmitter.emit(GetAccountAnalyticsByAccountIdQuery.class, (query)->query.getAccountId().equals(accountAnalytics.getAccountId()), accountAnalytics);

    }

}

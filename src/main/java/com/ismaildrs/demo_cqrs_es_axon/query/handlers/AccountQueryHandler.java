package com.ismaildrs.demo_cqrs_es_axon.query.handlers;

import com.ismaildrs.demo_cqrs_es_axon.query.dto.AccountStatementResponseDTO;
import com.ismaildrs.demo_cqrs_es_axon.query.entities.Account;
import com.ismaildrs.demo_cqrs_es_axon.query.entities.AccountOperation;
import com.ismaildrs.demo_cqrs_es_axon.query.queries.GetAccountStatementQuery;
import com.ismaildrs.demo_cqrs_es_axon.query.queries.GetAllAccountsQuery;
import com.ismaildrs.demo_cqrs_es_axon.query.queries.WatchEventQuery;
import com.ismaildrs.demo_cqrs_es_axon.query.repositories.AccountRepository;
import com.ismaildrs.demo_cqrs_es_axon.query.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Component
public class AccountQueryHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query) {
        return accountRepository.findAll();
    }

    @QueryHandler
    public AccountStatementResponseDTO on(GetAccountStatementQuery query) {
        return new AccountStatementResponseDTO(accountRepository.findById(query.accountId()).get(), operationRepository.findByAccountId(query.accountId()));
    }

    @QueryHandler
    public AccountOperation on(WatchEventQuery  query) {
        return AccountOperation.builder().build();
    }

}

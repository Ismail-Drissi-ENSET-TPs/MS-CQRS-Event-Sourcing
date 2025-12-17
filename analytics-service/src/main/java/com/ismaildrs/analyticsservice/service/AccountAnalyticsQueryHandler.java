package com.ismaildrs.analyticsservice.service;


import com.ismaildrs.analyticsservice.entities.AccountAnalytics;
import com.ismaildrs.analyticsservice.queries.GetAccountAnalyticsByAccountIdQuery;
import com.ismaildrs.analyticsservice.queries.GetAllAccountAnalyticsQuery;
import com.ismaildrs.analyticsservice.repo.AccountAnalyticsRepository;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AccountAnalyticsQueryHandler {
    private AccountAnalyticsRepository accountAnalyticsRepository;

    @QueryHandler
    public AccountAnalytics on(GetAccountAnalyticsByAccountIdQuery query){
        return accountAnalyticsRepository.findByAccountId(query.getAccountId());
    }

    @QueryHandler
    public List<AccountAnalytics> on(GetAllAccountAnalyticsQuery query){
        return accountAnalyticsRepository.findAll();
    }
}

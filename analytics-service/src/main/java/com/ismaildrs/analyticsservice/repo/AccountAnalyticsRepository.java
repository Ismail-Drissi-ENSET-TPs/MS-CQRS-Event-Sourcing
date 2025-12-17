package com.ismaildrs.analyticsservice.repo;

import com.ismaildrs.analyticsservice.entities.AccountAnalytics;
import com.ismaildrs.demo_cqrs_es_axon.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountAnalyticsRepository extends JpaRepository<AccountAnalytics, Long> {
    public AccountAnalytics findByAccountId(String accountId);
}

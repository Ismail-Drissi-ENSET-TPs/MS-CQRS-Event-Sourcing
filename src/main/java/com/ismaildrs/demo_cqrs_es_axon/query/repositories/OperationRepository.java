package com.ismaildrs.demo_cqrs_es_axon.query.repositories;

import com.ismaildrs.demo_cqrs_es_axon.query.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<AccountOperation , Integer> {
    List<AccountOperation> findByAccountId(String account_id);
}

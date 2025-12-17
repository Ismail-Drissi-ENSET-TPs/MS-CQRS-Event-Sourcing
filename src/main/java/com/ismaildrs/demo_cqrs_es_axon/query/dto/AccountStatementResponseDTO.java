package com.ismaildrs.demo_cqrs_es_axon.query.dto;

import com.ismaildrs.demo_cqrs_es_axon.query.entities.Account;
import com.ismaildrs.demo_cqrs_es_axon.query.entities.AccountOperation;

import java.util.List;

public record AccountStatementResponseDTO (Account account, List<AccountOperation> operationList){
}

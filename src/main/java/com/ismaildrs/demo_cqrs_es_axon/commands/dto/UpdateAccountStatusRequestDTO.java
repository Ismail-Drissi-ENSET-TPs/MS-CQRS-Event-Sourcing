package com.ismaildrs.demo_cqrs_es_axon.commands.dto;


import com.ismaildrs.demo_cqrs_es_axon.enums.AccountStatus;

public record UpdateAccountStatusRequestDTO(String accountId, AccountStatus accountStatus) {
}

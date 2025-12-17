package com.ismaildrs.demo_cqrs_es_axon.commands.dto;

public record CreditAccountRequestDTO(String accountId, double amount, String currency) {
}

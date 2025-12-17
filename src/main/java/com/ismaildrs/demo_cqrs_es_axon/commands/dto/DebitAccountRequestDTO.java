package com.ismaildrs.demo_cqrs_es_axon.commands.dto;

public record DebitAccountRequestDTO(String accountId, double amount, String currency) {
}

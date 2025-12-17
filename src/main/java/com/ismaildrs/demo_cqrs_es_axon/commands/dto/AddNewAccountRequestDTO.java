package com.ismaildrs.demo_cqrs_es_axon.commands.dto;

public record AddNewAccountRequestDTO(double intialBalance, String currency) {
}

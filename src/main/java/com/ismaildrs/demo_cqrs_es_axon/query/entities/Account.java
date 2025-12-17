package com.ismaildrs.demo_cqrs_es_axon.query.entities;

import com.ismaildrs.demo_cqrs_es_axon.enums.AccountStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    private String id;
    private double balance;
    private Instant createdAt;
    private String currency;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
}

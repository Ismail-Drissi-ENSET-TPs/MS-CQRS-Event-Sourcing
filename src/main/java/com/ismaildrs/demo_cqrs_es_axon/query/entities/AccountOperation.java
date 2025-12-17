package com.ismaildrs.demo_cqrs_es_axon.query.entities;

import com.ismaildrs.demo_cqrs_es_axon.enums.OperationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperation {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Instant date;
    private double amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    private Account account;
}

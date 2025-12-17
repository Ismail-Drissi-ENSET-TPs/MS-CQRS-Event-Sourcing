package com.ismaildrs.demo_cqrs_es_axon.query.repositories;

import com.ismaildrs.demo_cqrs_es_axon.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
}

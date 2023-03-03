package com.example.banking_ex_thymleaf.repository;

import com.example.banking_ex_thymleaf.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
}
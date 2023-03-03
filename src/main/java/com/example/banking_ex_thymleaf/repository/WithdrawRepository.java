package com.example.banking_ex_thymleaf.repository;

import com.example.banking_ex_thymleaf.model.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {
}
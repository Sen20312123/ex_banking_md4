package com.example.banking_ex_thymleaf.repository;

import com.example.banking_ex_thymleaf.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    @Query("SELECT SUM(t.feesAmount) FROM Transfer AS t")
    BigDecimal getSumFeesAmount();
}

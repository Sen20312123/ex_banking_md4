package com.example.banking_ex_thymleaf.service.withdraw;

import com.example.banking_ex_thymleaf.model.Withdraw;
import com.example.banking_ex_thymleaf.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class WithdrawService implements IWithdrawService {

    @Autowired
    private WithdrawRepository withdrawRepository;


    @Override
    public List<Withdraw> findAll() {
        return null;
    }

    @Override
    public Optional<Withdraw> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Withdraw save(Withdraw withdraw) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }


    @Override
    public void delete(Withdraw withdraw) {
    }
}
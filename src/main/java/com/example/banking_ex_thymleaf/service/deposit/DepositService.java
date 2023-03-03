package com.example.banking_ex_thymleaf.service.deposit;

import com.example.banking_ex_thymleaf.model.Deposit;
import com.example.banking_ex_thymleaf.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DepositService implements IDepositService {

    @Autowired
    private DepositRepository depositRepository;


    @Override
    public List<Deposit> findAll() {
        return depositRepository.findAll() ;
    }

    @Override
    public Optional<Deposit> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Deposit save(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    @Override
    public void deleteById(Long id) {
        depositRepository.deleteById(id);
    }

    @Override
    public void delete(Deposit deposit) {
        depositRepository.delete(deposit);
    }
}
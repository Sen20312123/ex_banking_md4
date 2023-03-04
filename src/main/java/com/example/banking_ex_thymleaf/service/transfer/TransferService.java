package com.example.banking_ex_thymleaf.service.transfer;

import com.example.banking_ex_thymleaf.model.Transfer;
import com.example.banking_ex_thymleaf.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransferService implements ITransferService {

    @Autowired
    private TransferRepository transferRepository;
    

    @Override
    public BigDecimal getSumFeesAmount() {
        return transferRepository.getSumFeesAmount();
    }

    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Optional<Transfer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Transfer save(Transfer transfer) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
    @Override
    public void delete(Transfer transfer) {
    }

}

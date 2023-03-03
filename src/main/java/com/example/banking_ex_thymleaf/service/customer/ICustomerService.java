package com.example.banking_ex_thymleaf.service.customer;

import com.example.banking_ex_thymleaf.model.Customer;
import com.example.banking_ex_thymleaf.model.Deposit;
import com.example.banking_ex_thymleaf.model.Transfer;
import com.example.banking_ex_thymleaf.model.Withdraw;
import com.example.banking_ex_thymleaf.service.IGeneralService;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {

    List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLike(String fullName, String email, String phone, String address);

     List<Customer> findAllByIdNot(Long id);
    List<Customer> findAllByDeletedIsFalse();
     Deposit deposit(Deposit deposit);

     Withdraw withdraw(Withdraw withdraw);

     Transfer transfer(Transfer transfer);
}

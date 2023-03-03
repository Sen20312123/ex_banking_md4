package com.example.banking_ex_thymleaf.repository;

import com.example.banking_ex_thymleaf.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLike(String fullName,
                                                                            String email,
                                                                            String phone,
                                                                            String address
    );

    List<Customer> findAllByIdNot(Long id);
    List<Customer> findAllByDeletedIsFalse();

}

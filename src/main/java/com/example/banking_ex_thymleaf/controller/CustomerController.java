package com.example.banking_ex_thymleaf.controller;

import com.example.banking_ex_thymleaf.model.Customer;
import com.example.banking_ex_thymleaf.model.Deposit;
import com.example.banking_ex_thymleaf.model.Transfer;
import com.example.banking_ex_thymleaf.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController{

    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public String showListPage(Model model) {

        List<Customer> customers = customerService.findAll();

        model.addAttribute("customers", customers);

        return "customer/list";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }
    @PostMapping("/create")
    public String doCreate(@ModelAttribute Customer customer, BindingResult bindingResult, Model model) {

        new Customer().validate(customer, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("customer", customer);
            model.addAttribute("error", true);
            return "customer/create";
        }

        customer.setBalance(BigDecimal.ZERO);
        customerService.save(customer);
        model.addAttribute("success", true);

        model.addAttribute("messages", "Create customer success");
        return "customer/create";
    }

    @GetMapping("/edit/{id}")
    public String showUpdatePage(@PathVariable Long id, Model model) {

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            model.addAttribute("error", true);
        } else {
            Customer customer = customerOptional.get();
            model.addAttribute("customer", customer);
        }
        return "customer/update";
    }


    @PostMapping("/edit/{id}")
    public String doUpdate(@PathVariable Long id, @ModelAttribute Customer customer, Model model) {

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            model.addAttribute("error", true);
        } else {
            customer.setId(id);
            customer.setBalance(BigDecimal.ZERO);
            customerService.save(customer);
            model.addAttribute("messageSuccess", "Update customer success");
            model.addAttribute("customer", customer);
        }
        return "customer/update";
    }

    @GetMapping("/delete/{id}")
    public String showDeletePage(@PathVariable Long id, Model model) {
        Optional<Customer> customerOptional = customerService.findById(id);
        if (!customerOptional.isPresent()) {
            model.addAttribute("error", true);
            List<Customer> customers = customerService.findAllByDeletedIsFalse();
            model.addAttribute("customers", customers);
            return "customer/list";
        } else {
            Customer customer = customerOptional.get();
            model.addAttribute("customer", customer);
            return "customer/delete";
        }
    }

    @PostMapping("/delete/{id}")
    public String doDelete(@PathVariable Long id, @ModelAttribute Customer customer, Model model) {

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            model.addAttribute("error", true);
            model.addAttribute("messageFail" ,"Not found customer" );
        } else {
            customer.setId(id);
            customerService.delete(customer);
            model.addAttribute("success" , true);
            model.addAttribute("messageSuccess", "Delete customer success");
            List<Customer> customers = customerService.findAllByDeletedIsFalse();
            model.addAttribute("customers", customers);
        }
        return "customer/list";
    }

    @GetMapping("/deposit/{id}")
    public String showDeposit(@PathVariable Long id , Model model){
        Optional<Customer> customerOptional = customerService.findById(id);
        if(!customerOptional.isPresent()){
            model.addAttribute("error" , true);
            model.addAttribute("messageSuccess" , "Not found customer");
            return "error/404";
        }
        else {
            Customer customer = customerOptional.get();
            Deposit deposit = new Deposit();
            deposit.setCustomer(customer);

            model.addAttribute("deposit" , deposit);
        }
        return "customer/deposit";
    }

    @PostMapping("/deposit/{customerId}")
    public String doDeposit(@PathVariable Long customerId, @Validated @ModelAttribute Deposit deposit ,  BindingResult bindingResult  , Model model){

        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("error", true);
            return "customer/deposit";
        }
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if(!customerOptional.isPresent()){
            model.addAttribute("error" , true);
            return "error/404";
        }
            BigDecimal transactionAmount = deposit.getTransactionAmount();
            Customer customer = customerOptional.get();
            BigDecimal currentBalance = customer.getBalance();
            BigDecimal newBalance = currentBalance.add(transactionAmount);
            customer.setBalance(newBalance);
            deposit.setCustomer(customer);
            customerService.deposit(deposit);
            deposit.setTransactionAmount(BigDecimal.ZERO);
            model.addAttribute("error" ,false);
           model.addAttribute("deposit" , deposit);

           model.addAttribute("success", true);
           model.addAttribute("messageSuccess" , "Deposit successful");
          return "customer/deposit";
    }

    @GetMapping("/transfer/{senderId}")
    public String showTransfer(@PathVariable Long senderId , Model model){
        Optional<Customer> customerOptional = customerService.findById(senderId);
        if (!customerOptional.isPresent()){
            model.addAttribute("error" , true);
            model.addAttribute("messageFail" , "Not found customer");
            return "error/404";
        }
        Customer sender = customerOptional.get();

        List<Customer> recipients = customerService.findAllByIdNot(senderId);
        Transfer transfer = new Transfer();
        transfer.setSender(sender);
        model.addAttribute("recipients" , recipients);
        model.addAttribute("transfer" , transfer);
        return "customer/transfer";
    }
    @PostMapping("/transfer/{senderId}")
    public String doTransfer(@PathVariable Long senderId , @ModelAttribute Transfer transfer , Model model){
        Optional<Customer> senderOptional = customerService.findById(senderId);
        if(!senderOptional.isPresent()){
            model.addAttribute("error" ,true);
            model.addAttribute("messageFail" , "Not found sender");
            return "error/404";
        }
        Optional<Customer> recipientOptional = customerService.findById(transfer.getRecipient().getId());
        if (!recipientOptional.isPresent()){
            model.addAttribute("error" ,true);
            model.addAttribute("messageFail" , "Not found recipient");
            return "error/404";
        }

        List<Customer> recipients = customerService.findAllByIdNot(senderId);
        model.addAttribute("recipients" , recipients);

        Customer sender = senderOptional.get();
        BigDecimal senderBalance = sender.getBalance();

        Customer recipient = recipientOptional.get();
        BigDecimal recipientBalance = recipient.getBalance();

        BigDecimal transferAmount = transfer.getTransferAmount();
        long fees = 10L;

        BigDecimal feesAmount = transferAmount.multiply(BigDecimal.valueOf(fees).divide(BigDecimal.valueOf(100l)));
        BigDecimal transactionAmount = transferAmount.add(feesAmount);

        BigDecimal minAmount = BigDecimal.valueOf(1000L);
        BigDecimal maxAmount = BigDecimal.valueOf(100000000L);

        if(transferAmount.compareTo(minAmount) < 0){
            model.addAttribute("error" , true);
            model.addAttribute("messageFail" , "Minimum transfer amount is $1000");
        }else if(transferAmount.compareTo(maxAmount) > 0){
            model.addAttribute("error" , true);
            model.addAttribute("messageFail" , "The maximum transfer amount is $100,000,000");
        }else {
            if (senderBalance.compareTo(transactionAmount) < 0){
                model.addAttribute("error" , true);
                model.addAttribute("messageFail" , "The balance is not enough to make the transaction");
                return "customer/transfer";
            }
            if (sender.getId().equals(recipient.getId())){
                model.addAttribute("error" , true);
                model.addAttribute("messageFail" , "Invalid recipient account");
                return "customer/transfer";
            }
            BigDecimal newSenderBalance =  senderBalance.subtract(transactionAmount);
            sender.setBalance(newSenderBalance);

            BigDecimal newRecipientBalance = recipientBalance.add(transferAmount);
            recipient.setBalance(newRecipientBalance);

            transfer.setSender(sender);
            transfer.setRecipient(recipient);
            transfer.setFees(fees);
            transfer.setFeesAmount(feesAmount);
            transfer.setTransactionAmount(transactionAmount);

            customerService.transfer(transfer);

            model.addAttribute("success", true);
            model.addAttribute("messageSuccess", "Successful transfer transaction");
        }
        return "customer/transfer";
    }

}
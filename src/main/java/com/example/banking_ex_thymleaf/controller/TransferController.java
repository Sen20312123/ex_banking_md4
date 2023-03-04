package com.example.banking_ex_thymleaf.controller;

import com.example.banking_ex_thymleaf.model.Transfer;
import com.example.banking_ex_thymleaf.service.transfer.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    private ITransferService transferService;

    @GetMapping
    public ModelAndView showListHistory() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("transfer/history");

        List<Transfer> transfers = transferService.findAll();
//
        BigDecimal getAllFeesAmount = transferService.getSumFeesAmount();
//
        modelAndView.addObject("transfers", transfers);
        modelAndView.addObject("allFeesAmount", getAllFeesAmount);

        return modelAndView;
    }

}

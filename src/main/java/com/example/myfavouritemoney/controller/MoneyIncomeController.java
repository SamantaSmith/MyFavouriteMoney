package com.example.myfavouritemoney.controller;

import com.example.myfavouritemoney.dto.CreateMoneyIncomeRequestDTO;
import com.example.myfavouritemoney.dto.CreateWalletRequestDTO;
import com.example.myfavouritemoney.entities.MoneyIncome;
import com.example.myfavouritemoney.entities.Wallet;
import com.example.myfavouritemoney.service.MoneyIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "moneyIncome")
public class MoneyIncomeController {

    @Autowired
    private MoneyIncomeService service;

    @PostMapping(path = "/add")
    public void createMoneyIncome(
            @RequestBody CreateMoneyIncomeRequestDTO dto
            //@AuthenticationPrincipal OAuth2User principal
    ) {
        service.addMoneyIncome(map(dto, 1L));
    }


    public MoneyIncome map(CreateMoneyIncomeRequestDTO dto, Long userId) {
        return new MoneyIncome(userId, dto.walletId(), dto.monthRegularity(), dto.income(), dto.standardDayOfIncomeInMonth());
    }

}

package com.example.myfavouritemoney.controller;

import com.example.myfavouritemoney.dto.CreateMoneyOperationRequestDTO;
import com.example.myfavouritemoney.dto.MoneyOperationDTO;
import com.example.myfavouritemoney.dto.WalletDTO;
import com.example.myfavouritemoney.entities.MoneyOperation;
import com.example.myfavouritemoney.service.MoneyOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "moneyOperation")
public class MoneyOperationController {

    @Autowired
    private MoneyOperationService service;

    //@PostMapping(path = "/add")
    //public void createMoneyOperation(
    //        @RequestBody CreateMoneyOperationRequestDTO dto
    //        //@AuthenticationPrincipal OAuth2User principal
    //) {
    //    service.addMoneyOperation(map(dto, 1L));
    //}

    @GetMapping(path = "/getExpenses")
    public List<MoneyOperationDTO> getExpenses() {
        return service.getExpenses();
    }

    public void updateChecked(Long id) {
        service.updateChecked(id);
    }


    //public MoneyOperation map(CreateMoneyOperationRequestDTO dto, Long userId) {
    //    return new MoneyOperation(userId, dto.walletId(), dto.monthRegularity(), dto.income(), dto.standardDayOfIncomeInMonth());
    //}

}

package com.example.myfavouritemoney.controller;

import com.example.myfavouritemoney.dto.CreateWalletRequestDTO;
import com.example.myfavouritemoney.dto.UpdateWalletRequestDTO;
import com.example.myfavouritemoney.dto.WalletDTO;
import com.example.myfavouritemoney.entities.Wallet;
import com.example.myfavouritemoney.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WalletController {

    @Autowired
    private WalletService service;


    public void createWallet(
                             @RequestBody CreateWalletRequestDTO dto
                             //@AuthenticationPrincipal OAuth2User principal
    ) {
        service.createOrUpdateWallet(map(dto, 1L));
    }


    public void deleteWallet(@RequestParam Long walletId) {
        service.deleteWallet(walletId);
    }


    public void updateWallet(@RequestBody UpdateWalletRequestDTO dto) {
        service.createOrUpdateWallet(new Wallet(dto.walletId(), dto.name(), 1L, dto.type(), dto.money()));
    }


    public List<WalletDTO> getWallets() {
        return service.getWallets(1L)
                .stream().map(e -> new WalletDTO(e.getName(), service.getWalletTypeVarchar(e.getType()), e.getMoney()))
                .collect(Collectors.toList());
    }

    public Boolean isActualizedWallets() {
        return service.isActualizedWallets(1L);
    }


    public Wallet map(CreateWalletRequestDTO dto, Long userId) {
        return new Wallet(dto.name(), userId, dto.type(), dto.initialMoney());
    }
}

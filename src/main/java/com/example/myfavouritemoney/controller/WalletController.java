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
@RequestMapping(path = "wallet")
public class WalletController {

    @Autowired
    private WalletService service;

    @PostMapping(path = "/add")
    public void createWallet(
                             @RequestBody CreateWalletRequestDTO dto
                             //@AuthenticationPrincipal OAuth2User principal
    ) {
        service.createOrUpdateWallet(map(dto, 1L));
    }

    @DeleteMapping(path = "/delete")
    public void deleteWallet(@RequestParam Long walletId) {
        service.deleteWallet(walletId);
    }

    @PutMapping(path = "/update")
    public void updateWallet(@RequestBody UpdateWalletRequestDTO dto) {
        service.createOrUpdateWallet(new Wallet(dto.walletId(), dto.name(), 1L, dto.type(), dto.money()));
    }

    @GetMapping(path = "/getByUserId")
    public List<WalletDTO> getWallets() {
        return service.getWallets(1L)
                .stream().map(e -> new WalletDTO(e.getName(), service.getWalletTypeVarchar(e.getType()), e.getMoney() + " ₽"))
                .collect(Collectors.toList());
    }


    public Wallet map(CreateWalletRequestDTO dto, Long userId) {
        return new Wallet(dto.name(), userId, dto.type(), dto.initialMoney());
    }
}

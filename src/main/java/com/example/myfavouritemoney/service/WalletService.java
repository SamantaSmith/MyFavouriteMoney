package com.example.myfavouritemoney.service;

import com.example.myfavouritemoney.entities.Wallet;
import com.example.myfavouritemoney.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository repository;

    public void createOrUpdateWallet(Wallet wallet) {
        repository.save(wallet);
    }
    public void deleteWallet(Long walletId) {
        repository.deleteById(walletId);
    }
    public List<Wallet> getWallets(Long userId) {
        return repository.findByUserId(userId);
    }
}

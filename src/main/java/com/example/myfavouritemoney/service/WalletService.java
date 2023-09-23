package com.example.myfavouritemoney.service;

import com.example.myfavouritemoney.entities.Wallet;
import com.example.myfavouritemoney.repository.WalletRepository;
import com.example.myfavouritemoney.repository.WalletTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository repository;

    @Autowired
    private WalletTypeRepository walletTypeRepository;

    public void createOrUpdateWallet(Wallet wallet) {
        repository.save(wallet);
    }
    public void deleteWallet(Long walletId) {
        repository.deleteById(walletId);
    }
    public List<Wallet> getWallets(Long userId) {
        return repository.findByUserId(userId);
    }
    public String getWalletTypeVarchar(Integer typeId) {
        return walletTypeRepository.getWalletTypeVarchar(typeId);
    }
}

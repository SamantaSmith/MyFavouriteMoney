package com.example.myfavouritemoney.repository;


import com.example.myfavouritemoney.entities.Wallet;
import com.example.myfavouritemoney.entities.WalletType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletTypeRepository extends JpaRepository<WalletType, Long> {

    @Query("SELECT t.typeVarchar from WalletType t WHERE t.typeInt = ?1")
    String getWalletTypeVarchar(Integer walletTypeId);
}

package com.example.myfavouritemoney.repository;

import com.example.myfavouritemoney.entities.RegularOperation;
import com.example.myfavouritemoney.entities.RegularOperationUnit;
import com.example.myfavouritemoney.enums.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RegularOperationUnitRepository extends JpaRepository<RegularOperationUnit, Long> {

    @Query("select rou from RegularOperationUnit rou\n" +
            "inner join RegularOperation ro on rou.baseRegularOperationId=ro.id\n" +
            "inner join MoneyOperation mo on ro.baseOperationId=mo.id\n" +
            "where mo.actual=true\n" +
            "and mo.operationType=?3\n" +
            "and to_char(rou.unitOperationDate, 'YYYY-MM') = CONCAT(?1, '-', ?2)")
    List<RegularOperationUnit> findRegularExpenses(String year, String month, String operationType);

    @Modifying
    @Transactional
    @Query("UPDATE RegularOperationUnit rou SET rou.completed = NOT rou.completed where rou.id = ?1")
    void updateChecked (UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE RegularOperationUnit t SET t.unitOperationDate = ?2, t.realAmountOfMoney = ?3 where t.id = ?1")
    void updateSingle(UUID id, LocalDate date, Float money);

    @Query("select rou.id from MoneyOperation rou\n" +
            "inner join RegularOperation ro on ro.baseOperationId=rou.id\n" +
            "inner join RegularOperationUnit mo on mo.baseRegularOperationId=ro.id\n" +
            "where mo.id=?1")
    UUID getBaseOperationId(UUID id);
}

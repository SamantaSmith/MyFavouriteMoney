package com.example.myfavouritemoney.repository;

import com.example.myfavouritemoney.entities.SingleOperation;
import com.example.myfavouritemoney.enums.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface SingleOperationRepository extends JpaRepository<SingleOperation, Long> {

    @Query("SELECT so FROM SingleOperation so\n" +
            "    inner join MoneyOperation m on so.baseOperationId=m.id\n" +
            "    where m.actual=true\n" +
            "    and m.operationType=?3\n" +
            "    and to_char(so.date, 'YYYY-MM') = CONCAT(?1, '-', ?2)")
    List<SingleOperation> findSingleExpenses(String year, String month, String operationType);

    @Modifying
    @Transactional
    @Query("UPDATE SingleOperation t SET t.completed = NOT t.completed where t.id = ?1")
    void updateChecked (UUID id);
}

package com.example.myfavouritemoney.repository;

import com.example.myfavouritemoney.entities.MonitoredCategory;
import com.example.myfavouritemoney.entities.MonitoredCategoryUnit;
import com.example.myfavouritemoney.entities.SingleOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface MonitoredCategoryUnitRepository extends JpaRepository<MonitoredCategoryUnit, Long> {

    @Query("SELECT so FROM MonitoredCategoryUnit so\n" +
            "    inner join MonitoredCategory m on so.baseUUID=m.id\n" +
            "    where to_char(so.month, 'YYYY-MM') = CONCAT(?1, '-', ?2)")
    List<MonitoredCategoryUnit> findByMonth(String year, String month);

    @Modifying
    @Transactional
    @Query("UPDATE MonitoredCategoryUnit t SET t.currentExpense = t.currentExpense + ?2 where t.id = ?1")
    void addExpense(UUID id, Float money);
}

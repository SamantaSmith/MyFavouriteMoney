package com.example.myfavouritemoney.repository;

import com.example.myfavouritemoney.entities.MonitoredCategory;
import com.example.myfavouritemoney.entities.MonitoredCategoryUnit;
import com.example.myfavouritemoney.entities.SingleOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoredCategoryUnitRepository extends JpaRepository<MonitoredCategoryUnit, Long> {

    @Query("SELECT so FROM MonitoredCategoryUnit so\n" +
            "    inner join MonitoredCategory m on so.baseUUID=m.id\n" +
            "    where to_char(so.month, 'YYYY-MM') = CONCAT(?1, '-', ?2)")
    List<MonitoredCategoryUnit> findByMonth(String year, String month);
}

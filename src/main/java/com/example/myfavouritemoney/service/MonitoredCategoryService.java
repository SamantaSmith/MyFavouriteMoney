package com.example.myfavouritemoney.service;

import com.example.myfavouritemoney.dto.MonitoredCategoryDTO;
import com.example.myfavouritemoney.repository.MonitoredCategoryRepository;
import com.example.myfavouritemoney.repository.MonitoredCategoryUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoredCategoryService {

    @Autowired
    private MonitoredCategoryRepository baseRepository;
    @Autowired
    private MonitoredCategoryUnitRepository unitRepository;

    public List<MonitoredCategoryDTO> getCategories (int year, int month) {
        return unitRepository.findByMonth(String.valueOf(year), String.valueOf(month))
                .stream()
                .map(e -> new MonitoredCategoryDTO(e.getId(), e.getMonth(), e.getName(), e.getCurrentExpense(), e.getMonthLimit()))
                .toList();
    }
}

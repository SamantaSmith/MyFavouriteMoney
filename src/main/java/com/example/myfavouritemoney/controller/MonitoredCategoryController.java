package com.example.myfavouritemoney.controller;

import com.example.myfavouritemoney.dto.MonitoredCategoryDTO;
import com.example.myfavouritemoney.service.MonitoredCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MonitoredCategoryController {

    @Autowired
    private MonitoredCategoryService service;

    public List<MonitoredCategoryDTO> getCategories(int year, int month) {
        return service.getCategories(year, month);
    }


}

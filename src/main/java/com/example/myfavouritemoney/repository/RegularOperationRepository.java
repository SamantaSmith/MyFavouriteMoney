package com.example.myfavouritemoney.repository;

import com.example.myfavouritemoney.entities.RegularOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularOperationRepository extends JpaRepository<RegularOperation, Long> {


}

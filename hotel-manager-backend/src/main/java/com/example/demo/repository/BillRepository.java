package com.example.demo.repository;

import com.example.demo.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findAllByTenantId(long id);

    @Query("SELECT date, COUNT(date), SUM(price) FROM Bill WHERE date >= :fromDate GROUP BY date ORDER BY date ASC")
    List<List<Object>> getStatistics(LocalDate fromDate);

}

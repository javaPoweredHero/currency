package com.test.task.dao.repository;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.task.dao.domain.CurrencyRecord;

public interface CurrencyRepository extends JpaRepository<CurrencyRecord, Long> {
    Set<CurrencyRecord> findAllByDate(LocalDate date);
}

package com.factor.it.ecommerce.repository;

import com.factor.it.ecommerce.model.SpecialDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SpecialDateRepository extends JpaRepository<SpecialDate,Long> {

    Optional<SpecialDate> findByDate(LocalDate date);
}

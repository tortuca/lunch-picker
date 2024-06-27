package com.tortuca.lunchpicker.repository;

import com.tortuca.lunchpicker.model.LunchPlan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LunchPlanRepository extends CrudRepository<LunchPlan, UUID>{
    LunchPlan findByCode(String code);
    LunchPlan findByCodeAndActive(String code, boolean active);
}


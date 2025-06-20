package com.ufcg.psoft.commerce.repositories;

import com.ufcg.psoft.commerce.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
}

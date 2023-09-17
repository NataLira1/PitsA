package com.ufcg.psoft.commerce.repositories;

import com.ufcg.psoft.commerce.models.Entregador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociacaoRepository extends JpaRepository<Entregador, Long> {
}

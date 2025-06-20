package com.ufcg.psoft.commerce.repositories;

import com.ufcg.psoft.commerce.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
}

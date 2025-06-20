package com.ufcg.psoft.commerce.repositories;

import com.ufcg.psoft.commerce.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}

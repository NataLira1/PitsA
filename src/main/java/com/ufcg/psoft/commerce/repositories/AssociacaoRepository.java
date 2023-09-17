package com.ufcg.psoft.commerce.repositories;

import com.ufcg.psoft.commerce.models.Associacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociacaoRepository extends JpaRepository<Associacao, Long> {

    Associacao findByEntregadorIdAndEstabelecimentoId(Long entregadorId, Long estabelecimentoId);
}

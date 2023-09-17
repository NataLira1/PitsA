package com.ufcg.psoft.commerce.repositories;

import com.ufcg.psoft.commerce.models.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento ,Long> {

    public Estabelecimento findByCodigoAcesso(String codigoAcesso);

    public Estabelecimento deleteByCodigoAcesso(String codigoAcesso);
}

package com.ufcg.psoft.commerce.service.associacao;

import com.ufcg.psoft.commerce.models.Associacao;

@FunctionalInterface
public interface EstabelecimentoRejeitaAssociacaoService {

    Associacao rejeitar(Long entregadorId, Long estabelecimentoId, String codigoAcessoEstabelecimento);
}

package com.ufcg.psoft.commerce.service.associacao;

import com.ufcg.psoft.commerce.models.Associacao;

@FunctionalInterface
public interface EstabelecimentoAprovaAssociacaoService {

    Associacao aprovar(Long entregadorId, Long estabelecimentoId, String codigoAcessoEstabelecimento);
}

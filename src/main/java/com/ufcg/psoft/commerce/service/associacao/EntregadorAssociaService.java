package com.ufcg.psoft.commerce.service.associacao;

import com.ufcg.psoft.commerce.models.Associacao;

@FunctionalInterface
public interface EntregadorAssociaService {

    Associacao associar(Long entregadorId, String codigoAcessoEntregador, Long estabelecimentoId);
}

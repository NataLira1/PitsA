package com.ufcg.psoft.commerce.service.associacao;

import com.ufcg.psoft.commerce.models.Entregador;

@FunctionalInterface
public interface EntregadorAssociaService {

    Entregador associar(Long entregadorId, String codigoAcessoEntregador, String codigoAcessoEstabelecimento);
}

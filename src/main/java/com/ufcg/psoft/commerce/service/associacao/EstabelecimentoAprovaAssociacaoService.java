package com.ufcg.psoft.commerce.service.associacao;

@FunctionalInterface
public interface EstabelecimentoAprovaAssociacaoService {

    void aprovar(Long entregadorId, Long estabelecimentoId, String codigoAcessoEstabelecimento);
}

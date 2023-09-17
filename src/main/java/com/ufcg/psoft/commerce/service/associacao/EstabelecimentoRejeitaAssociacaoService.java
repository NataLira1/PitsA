package com.ufcg.psoft.commerce.service.associacao;

@FunctionalInterface
public interface EstabelecimentoRejeitaAssociacaoService {

    void rejeitar(Long entregadorId, Long estabelecimentoId, String codigoAcessoEstabelecimento);
}

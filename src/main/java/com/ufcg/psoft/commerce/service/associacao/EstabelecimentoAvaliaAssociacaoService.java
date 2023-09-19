package com.ufcg.psoft.commerce.service.associacao;

@FunctionalInterface
public interface EstabelecimentoAvaliaAssociacaoService {

    boolean avaliar(Long entregadorId, Long estabelecimentoId, String codigoAcessoEstabelecimento, boolean status);
}

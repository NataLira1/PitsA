package com.ufcg.psoft.commerce.service.sabor;

@FunctionalInterface
public interface SaborDeletarService {
    public void deletar(Long idEstabelecimento, String codigoAcesso, Long id);
}

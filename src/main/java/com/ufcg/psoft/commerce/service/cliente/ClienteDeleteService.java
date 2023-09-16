package com.ufcg.psoft.commerce.service.cliente;

@FunctionalInterface
public interface ClienteDeleteService {

    public void excluir(Long id, String codigoAcesso);

}

package com.ufcg.psoft.commerce.service.EstabelecimentoServices;


@FunctionalInterface
public interface EstabelecimentoDeleteService {

    void delete(Long id, String codigo);
}

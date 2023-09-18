package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.dto.Cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.models.Cliente;

@FunctionalInterface
public interface ClienteCriarService {
    public Cliente criar(ClientePostPutRequestDTO clientePostPutRequestDTO);
}

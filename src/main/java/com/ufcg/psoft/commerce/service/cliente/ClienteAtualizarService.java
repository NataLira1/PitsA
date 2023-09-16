package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.dto.Cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.models.Cliente;

@FunctionalInterface
public interface ClienteAtualizarService {

    public Cliente atualizar(Long id, String codigoAcesso,ClientePostPutRequestDTO clientePostPutRequestDTO);

}

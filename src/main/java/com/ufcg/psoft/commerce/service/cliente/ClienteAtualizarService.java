package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.dto.Cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.models.Cliente;

@FunctionalInterface
public interface ClienteAtualizarService {

    public ClienteResponseDTO atualizar(Long id, String codigoAcesso, String usuario, ClientePostPutRequestDTO clientePostPutRequestDTO);

}

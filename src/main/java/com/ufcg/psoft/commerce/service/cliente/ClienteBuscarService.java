package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.models.Cliente;

import java.util.List;


public interface ClienteBuscarService {

    public ClienteResponseDTO buscar(Long id);

    public List<ClienteResponseDTO> buscarTudo();

}

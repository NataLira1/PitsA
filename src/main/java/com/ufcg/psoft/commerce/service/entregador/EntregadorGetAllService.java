package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.Entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;

import java.util.List;

@FunctionalInterface
public interface EntregadorGetAllService {
    List<EntregadorResponseDTO> getAll();
}

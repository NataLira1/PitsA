package com.ufcg.psoft.commerce.service.entregador;

import org.apache.catalina.connector.Response;

import com.ufcg.psoft.commerce.dto.Entregador.EntregadorDisponibilidadeDTO;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;

@FunctionalInterface
public interface EntregadorPutDisponibilidadeService {
    EntregadorResponseDTO mudar(Long entregadorId, String codigoAcesso, EntregadorDisponibilidadeDTO entregadorDisponibilidadeDTO);
}

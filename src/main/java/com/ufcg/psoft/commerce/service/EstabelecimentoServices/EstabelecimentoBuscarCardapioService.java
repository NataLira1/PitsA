package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import com.ufcg.psoft.commerce.dto.sabor.SaborInteresseResponseDTO;

import java.util.Set;


public interface EstabelecimentoBuscarCardapioService {

    Set<SaborInteresseResponseDTO> getCardapio(Long id);

    Set<SaborInteresseResponseDTO> getCardapioPorTipo(Long id, String tipo);

    Set<SaborInteresseResponseDTO> getCardapioPorDisponibilidade(Long id, Boolean disponivel);
}

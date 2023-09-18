package com.ufcg.psoft.commerce.service.entregador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.repositories.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorV1CriarService implements EntregadorCriarService{
    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public EntregadorResponseDTO criarEntregador(EntregadorPostPutRequestDTO novoEntregador) {
        return mapper.convertValue(entregadorRepository.saveAndFlush(mapper.convertValue(novoEntregador, Entregador.class)), EntregadorResponseDTO.class);
    }
}

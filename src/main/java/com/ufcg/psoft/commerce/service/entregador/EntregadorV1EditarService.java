package com.ufcg.psoft.commerce.service.entregador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.repositories.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorV1EditarService implements EntregadorEditarService{
    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public EntregadorResponseDTO editarEntregador(Entregador entregador, EntregadorPostPutRequestDTO novoEntregador) {
        entregador.setVeiculo(novoEntregador.getVeiculo());
        entregador.setNome(novoEntregador.getNome());

        return mapper.convertValue(novoEntregador, EntregadorResponseDTO.class);
    }
}

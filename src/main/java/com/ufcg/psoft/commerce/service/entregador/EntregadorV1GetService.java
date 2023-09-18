package com.ufcg.psoft.commerce.service.entregador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.repositories.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorV1GetService implements EntregadorGetService{
    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public Entregador getEntregador(Long id) {
        return mapper.convertValue(entregadorRepository.findById(id), Entregador.class);
    }
}

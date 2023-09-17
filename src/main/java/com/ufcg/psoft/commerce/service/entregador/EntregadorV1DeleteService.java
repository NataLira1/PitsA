package com.ufcg.psoft.commerce.service.entregador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.repositories.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorV1DeleteService implements EntregadorDeleteService{
    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void deletar(Long id) {
        entregadorRepository.deleteById(id);
    }
}

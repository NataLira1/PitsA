package com.ufcg.psoft.commerce.service.entregador;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.repositories.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregadorV1GetAllService implements EntregadorGetAllService {

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<EntregadorResponseDTO> getAll() {
        return mapper.convertValue(entregadorRepository.findAll(), new TypeReference<>() {
        });
    }
}

package com.ufcg.psoft.commerce.service.cliente;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteV1BuscarService implements  ClienteBuscarService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public ClienteResponseDTO buscar(Long id) {

        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoExisteException());

        return ClienteResponseDTO.builder()
                .id(cliente.getId())
                .nomeCompleto(cliente.getNomeCompleto())
                .endereco(cliente.getEndereco())
                .usuario(cliente.getUsuario())
                .build();
    }

    @Override
    public List<ClienteResponseDTO> buscarTudo() {

        //return clienteRepository.findAll();
        List<ClienteResponseDTO> clienteResponseDTOS = new ArrayList<>();

        clienteRepository.findAll().forEach(
                (cl) -> clienteResponseDTOS.add(
                        ClienteResponseDTO.builder()
                                .id(cl.getId())
                                .endereco(cl.getEndereco())
                                .usuario(cl.getUsuario())
                                .nomeCompleto(cl.getNomeCompleto())
                                .build()
                )
        );
        return clienteResponseDTOS;
    }
}

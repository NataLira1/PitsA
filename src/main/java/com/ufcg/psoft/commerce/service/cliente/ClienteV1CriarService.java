package com.ufcg.psoft.commerce.service.cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteV1CriarService implements  ClienteCriarService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    @Transactional
    public ClienteResponseDTO criar(ClientePostPutRequestDTO clientePostPutRequestDTO) {

        if(clientePostPutRequestDTO.getCodigoAcesso().length() == 6){
            Cliente cliente = Cliente.builder()
                    .codigoAcesso(clientePostPutRequestDTO.getCodigoAcesso())
                    .endereco(clientePostPutRequestDTO.getEndereco())
                    .usuario(clientePostPutRequestDTO.getUsuario())
                    .nomeCompleto(clientePostPutRequestDTO.getNomeCompleto())
                    .build();

            clienteRepository.save(cliente);

            return ClienteResponseDTO.builder()
                    .id(cliente.getId())
                    .nomeCompleto(cliente.getNomeCompleto())
                    .endereco(cliente.getEndereco())
                    .usuario(cliente.getUsuario())
                    .build();
        }else{
            throw new CodigoAcessoInvalidException();
        }
    }
}

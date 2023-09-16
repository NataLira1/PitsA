package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.dto.Cliente.ClientePostPutRequestDTO;
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

    @Override
    @Transactional
    public Cliente criar(ClientePostPutRequestDTO clientePostPutRequestDTO) {

        if(clientePostPutRequestDTO.getCodigoAcesso().length() == 6){
            Cliente cliente = Cliente.builder()
                    .codigoAcesso(clientePostPutRequestDTO.getCodigoAcesso())
                    .endereco(clientePostPutRequestDTO.getEndereco())
                    .usuario(clientePostPutRequestDTO.getUsuario())
                    .nomeCompleto(clientePostPutRequestDTO.getNomeCompleto())
                    .build();

            return clienteRepository.save(cliente);
        }else{
            throw new CodigoAcessoInvalidException();
        }
    }
}

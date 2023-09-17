package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.dto.Cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.CodigoDeAcessoNumericoException;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteV1AtualizarService implements ClienteAtualizarService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente atualizar(Long id, String codigoAcesso, String usuario,ClientePostPutRequestDTO clientePostPutRequestDTO) {

        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoExisteException());


        if(!codigoAcesso.equals(cliente.getCodigoAcesso()) || usuario.equals(cliente.getUsuario())){
            throw new CodigoAcessoInvalidException();
        }

        cliente.setCodigoAcesso(clientePostPutRequestDTO.getCodigoAcesso());
        cliente.setEndereco(clientePostPutRequestDTO.getEndereco());
        cliente.setNomeCompleto(clientePostPutRequestDTO.getNomeCompleto());
        cliente.setUsuario(clientePostPutRequestDTO.getUsuario());

        return clienteRepository.save(cliente);
    }
}

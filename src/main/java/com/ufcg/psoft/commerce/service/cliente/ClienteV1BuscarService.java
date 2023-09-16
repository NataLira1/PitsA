package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteV1BuscarService implements  ClienteBuscarService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente buscar(Long id) {

        Optional<Cliente> clienteOp = clienteRepository.findById(id);

        if(!clienteOp.isPresent()){
            throw new ClienteNaoExisteException();
        }

        return clienteOp.get();
    }

    @Override
    public List<Cliente> buscarTudo() {
        return clienteRepository.findAll();
    }
}

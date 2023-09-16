package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteV1DeleteService implements ClienteDeleteService{

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void excluir(Long id, String codigoAcesso) {

        Optional<Cliente> clienteOp = clienteRepository.findById(id);

        if(!clienteOp.isPresent()){
            throw new ClienteNaoExisteException();
        }
        if(codigoAcesso.equals(clienteRepository.findById(id).get().getCodigoAcesso())){
            clienteRepository.deleteById(id);
        } else {
            throw new CodigoAcessoInvalidException();
        }
    }
}

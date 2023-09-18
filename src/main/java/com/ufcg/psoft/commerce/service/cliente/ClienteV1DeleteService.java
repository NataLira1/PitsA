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
    public void excluir(Long id, String codigoAcesso, String usuario) {

        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoExisteException());

        if(!codigoAcesso.equals(cliente.getCodigoAcesso()) || usuario.equals(cliente.getUsuario())){
            throw new CodigoAcessoInvalidException();
        }
        clienteRepository.deleteById(id);

       // if(codigoAcesso.equals(cliente.getCodigoAcesso())){
       //     clienteRepository.deleteById(id);
       // } else {
       //     throw new CodigoAcessoInvalidException();
       // }
    }
}

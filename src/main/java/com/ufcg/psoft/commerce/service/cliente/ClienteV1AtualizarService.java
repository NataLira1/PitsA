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
    public Cliente atualizar(Long id, String codigoAcesso, ClientePostPutRequestDTO clientePostPutRequestDTO) {

        Optional<Cliente> clienteOp = clienteRepository.findById(id);

        if(!clienteOp.isPresent()){
            throw new ClienteNaoExisteException();
        }

        Cliente cliente = clienteOp.get();

        if(codigoAcesso.equals(cliente.getCodigoAcesso())){

            if(clientePostPutRequestDTO.getCodigoAcesso().length() == 6 && saoNumericos(clientePostPutRequestDTO.getCodigoAcesso())){

                cliente.setCodigoAcesso(clientePostPutRequestDTO.getCodigoAcesso());
                cliente.setEndereco(clientePostPutRequestDTO.getEndereco());
                cliente.setNomeCompleto(clientePostPutRequestDTO.getNomeCompleto());
                cliente.setUsuario(clientePostPutRequestDTO.getUsuario());

                return clienteRepository.save(cliente);
            }else {
                throw new CodigoDeAcessoNumericoException();
            }

        } else {
            throw new CodigoAcessoInvalidException();
        }

    }

    public boolean saoNumericos(String chaveAcesso){
        for (char c : chaveAcesso.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

}

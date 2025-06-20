package com.ufcg.psoft.commerce.service.pedido;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.ClientesDistintosException;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.PedidoNaoExisteException;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;

@Service
public class PedidoV1DeletarClienteService implements PedidoDeletarClienteService{
    
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;


    @Override
    public void deletar(Long pedidoId, Long clienteId, String clienteCodigoAcesso) {
        
        Optional<Pedido> pedidoOp = pedidoRepository.findById(pedidoId);
    
        if(!pedidoOp.isPresent()){
            throw new PedidoNaoExisteException();
        }

        Pedido pedido = pedidoOp.get();
        
        Optional<Cliente> clienteOp = clienteRepository.findById(clienteId);

        if(!clienteOp.isPresent()){
            throw new ClienteNaoExisteException();
        }

        Cliente cliente = clienteOp.get();

        if(!cliente.equals(pedido.getCliente())){
            throw new ClientesDistintosException();
        }

        if(!pedido.getCliente().getCodigoAcesso().equals(clienteCodigoAcesso)){
            throw new CodigoAcessoInvalidException();
        }

        pedidoRepository.deleteById(pedidoId);


    }

}

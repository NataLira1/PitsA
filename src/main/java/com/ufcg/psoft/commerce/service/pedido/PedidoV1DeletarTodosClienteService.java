package com.ufcg.psoft.commerce.service.pedido;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;

@Service
public class PedidoV1DeletarTodosClienteService implements PedidoDeletarTodosClienteService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;


    @Override
    public void deletarTodos(Long clienteId, String clienteCodigoAcesso) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        
        Optional<Cliente> clienteOp = clienteRepository.findById(clienteId);

        if(!clienteOp.isPresent()){
            throw new ClienteNaoExisteException();
        
        }

        Cliente cliente = clienteOp.get();

        if(!cliente.getCodigoAcesso().equals(clienteCodigoAcesso)){
            throw new CodigoAcessoInvalidException();
        }
        
        for(Pedido pedido: pedidos){
            if(pedido.getCliente().equals(cliente)){
                pedidoRepository.deleteById(pedido.getId());
            }
        }
    }

    
}

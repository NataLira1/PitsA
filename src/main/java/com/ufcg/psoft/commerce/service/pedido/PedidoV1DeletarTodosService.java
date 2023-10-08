package com.ufcg.psoft.commerce.service.pedido;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.IdInvalidoClienteEstabelecimentoException;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;

@Service
public class PedidoV1DeletarTodosService implements PedidoDeletarTodosService {
    
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ClienteRepository clienteRepository;


    @Override
    public void deletarTodos(Long clienteEstabelecimentoId, String codigoAcesso) {
        
        List<Pedido> pedidos = pedidoRepository.findAll();
        
        Optional<Cliente> clienteOp = clienteRepository.findById(clienteEstabelecimentoId);
        Optional<Estabelecimento> estabelecimentoOp = estabelecimentoRepository.findById(clienteEstabelecimentoId);

        if(!clienteOp.isPresent() && !estabelecimentoOp.isPresent()){
            throw new IdInvalidoClienteEstabelecimentoException();
        }
        else if(clienteOp.isPresent()){
            Cliente cliente = clienteOp.get();

            for(Pedido pedido : pedidos){
                if(pedido.getCliente().equals(cliente)){
                    pedidoRepository.deleteById(pedido.getId());
                }
            }
        }
        else{
            Estabelecimento estabelecimento = estabelecimentoOp.get();

            for(Pedido pedido: pedidos){
                if(pedido.getCliente().equals(estabelecimento)){
                    pedidoRepository.deleteById(clienteEstabelecimentoId);
                }
            }
        }

    }





    
}

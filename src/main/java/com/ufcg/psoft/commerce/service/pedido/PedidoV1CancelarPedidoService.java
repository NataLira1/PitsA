package com.ufcg.psoft.commerce.service.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.PedidoNaoExisteException;
import com.ufcg.psoft.commerce.exception.PedidoNaoPodeSerCanceladoException;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;

@Service
public class PedidoV1CancelarPedidoService implements PedidoCancelarPedidoService{


    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;


    @Override
    public void cancelar(Long pedidoId, String clienteCodigoAcesso) {
        
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoExisteException());

        if(!pedido.getCliente().getCodigoAcesso().equals(clienteCodigoAcesso)){
            throw new CodigoAcessoInvalidException();
        }
        if(pedido.getStatus().equals("Pedido pronto")){
            throw new  PedidoNaoPodeSerCanceladoException();
        }

        pedidoRepository.deleteById(pedidoId);

        
        
    }
    
}

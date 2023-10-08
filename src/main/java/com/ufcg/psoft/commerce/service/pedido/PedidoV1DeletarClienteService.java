package com.ufcg.psoft.commerce.service.pedido;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.ClientesDistintosException;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.EstabelecimentosDistintosException;
import com.ufcg.psoft.commerce.exception.IdInvalidoClienteEstabelecimentoException;
import com.ufcg.psoft.commerce.exception.PedidoNaoExisteException;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;

@Service
public class PedidoV1DeletarClienteService implements PedidoDeletarClienteService{


    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;


    @Override
    public void deletar(Long pedidoId, Long clienteEstabelecimentoId, String codigoAcesso) {
        
        Optional<Pedido> pedidoOp = pedidoRepository.findById(pedidoId);
    
        if(!pedidoOp.isPresent()){
            throw new PedidoNaoExisteException();
        }

        Pedido pedido = pedidoOp.get();
        
        Optional<Cliente> clienteOp = clienteRepository.findById(clienteEstabelecimentoId);
        Optional<Estabelecimento> estabelecimentoOp = estabelecimentoRepository.findById(clienteEstabelecimentoId);

        if(!clienteOp.isPresent() && !estabelecimentoOp.isPresent()){
            throw new IdInvalidoClienteEstabelecimentoException();
        }

        else if(clienteOp.isPresent()){
            Cliente cliente = clienteOp.get();

            if(!pedido.getCliente().equals(cliente)){
                throw new ClientesDistintosException();
            }
    
    
            if(!pedido.getCliente().getCodigoAcesso().equals(codigoAcesso)){
                throw new CodigoAcessoInvalidException();
            }

        }
        else{
            Estabelecimento estabelecimento = estabelecimentoOp.get();

            if(!pedido.getEstabelecimento().equals(estabelecimento)){
                throw new EstabelecimentosDistintosException();
            }
    
    
            if(!pedido.getEstabelecimento().getCodigoAcesso().equals(codigoAcesso)){
                throw new CodigoAcessoInvalidException();
            }
        }
        

        pedidoRepository.deleteById(pedidoId);


    }
    
}

package com.ufcg.psoft.commerce.service.pedido;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentosDistintosException;
import com.ufcg.psoft.commerce.exception.PedidoNaoExisteException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;

@Service
public class PedidoV1DeletarEstabelecimentoService implements PedidoDeletarEstabelecimentoService{


    @Autowired
    PedidoRepository pedidoRepository;


    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;


    @Override
    public void deletar(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso) {
        Optional<Pedido> pedidoOp = pedidoRepository.findById(pedidoId);
    
        if(!pedidoOp.isPresent()){
            throw new PedidoNaoExisteException();
        }

        Pedido pedido = pedidoOp.get();
        
        Optional<Estabelecimento> estabelecimentoOp = estabelecimentoRepository.findById(estabelecimentoId);

        if(!estabelecimentoOp.isPresent()){
            throw new EstabelecimentoNaoEncontradoException();
        }

        Estabelecimento estabelecimento = estabelecimentoOp.get();

        if(!estabelecimento.equals(pedido.getEstabelecimento())){
            throw new EstabelecimentosDistintosException();
        }

        if(!pedido.getEstabelecimento().getCodigoAcesso().equals(estabelecimentoCodigoAcesso)){
            throw new CodigoAcessoInvalidException();
        }

        pedidoRepository.deleteById(pedidoId);

    }
    
}

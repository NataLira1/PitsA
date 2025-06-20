package com.ufcg.psoft.commerce.service.pedido;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;

@Service
public class PedidoV1DeletarTodosEstabelecimentoService implements PedidoDeletarTodosEstabelecimentoService {
    
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public void deletarTodos(Long estabelecimentoId, String estabelecimentoCodigoAesso) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        
        Optional<Estabelecimento> estabelecimentoOp = estabelecimentoRepository.findById(estabelecimentoId);

        if(!estabelecimentoOp.isPresent()){
            throw new EstabelecimentoNaoEncontradoException();
        
        }

        Estabelecimento estabelecimento = estabelecimentoOp.get();

        if(!estabelecimento.getCodigoAcesso().equals(estabelecimentoCodigoAesso)){
            throw new CodigoAcessoInvalidException();
        }
        
        for(Pedido pedido: pedidos){
            if(pedido.getEstabelecimento().equals(estabelecimento)){
                pedidoRepository.deleteById(pedido.getId());
            }
        }


    }
}

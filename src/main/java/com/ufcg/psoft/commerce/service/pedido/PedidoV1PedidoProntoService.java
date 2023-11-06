package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.*;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1PedidoProntoService implements PedidoProntoService{

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Override
    public PedidoResponseDTO finalizado(Long pedidoId, Long estabelecimentoId,String codigoAcessoEstabelecimento) {

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(() -> new EstabelecimentoNaoEncontradoException());

        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(()->new PedidoNaoExisteException());

        if(!(estabelecimento.getCodigoAcesso().equals(codigoAcessoEstabelecimento))){
            throw new CodigoAcessoInvalidException();
        }

        if(!(pedido.getEstabelecimento().getCodigoAcesso().equals(codigoAcessoEstabelecimento))){
            throw new CodigoAcessoInvalidException();
        }

        if(!pedido.isStatusPagamento()){
            throw new PagamentoNaoAutorizadoExeption();
        }

        if(!pedido.getStatus().toUpperCase().equals("PEDIDO EM PREPARO")){
            throw  new PulandoEtapasExeption();
        }

        pedido.setStatus("Pedido pronto");

        pedidoRepository.save(pedido);

        return PedidoResponseDTO.builder()
                .preco(pedido.getPreco())
                .cliente(pedido.getCliente())
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .estabelecimento(pedido.getEstabelecimento())
                .pizzas(pedido.getPizzas())
                .status(pedido.getStatus())
                .statusPagamento(pedido.isStatusPagamento())
                .build();

    }
}

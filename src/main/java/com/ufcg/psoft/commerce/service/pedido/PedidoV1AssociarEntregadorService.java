package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.PedidoNaoExisteException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1AssociarEntregadorService implements PedidoAssociarEntregadorService{
    @Autowired
    PedidoRepository pedidoRepository;

    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public PedidoResponseDTO associar(Long pedidoId, Long estabelecimentoId, String codigoAcessoEstabelecimento) {

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(() -> new EstabelecimentoNaoEncontradoException());

        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(()->new PedidoNaoExisteException());

        if(!estabelecimento.getCodigoAcesso().equals(codigoAcessoEstabelecimento)){
            throw new CodigoAcessoInvalidException();
        }



        //como faço para verificar se o pedido foi associado a um entregador, ou não precisa!!!!!

        pedido.setStatus("Pedido em rota");

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

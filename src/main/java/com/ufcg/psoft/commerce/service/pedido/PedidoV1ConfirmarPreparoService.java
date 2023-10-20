package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.PagamentoNaoAutorizadoExeption;
import com.ufcg.psoft.commerce.exception.PedidoNaoExisteException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1ConfirmarPreparoService implements PedidoConfirmarPreparoService{

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public PedidoResponseDTO confirmarPreparo(Long pedidoId, Long estabelecimentoId,String codigoAcessoEstabelecimento) {

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(() -> new EstabelecimentoNaoEncontradoException());

        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(()->new PedidoNaoExisteException());
        boolean statusPagamento = pedido.isStatusPagamento();

        if(!statusPagamento){
            throw new PagamentoNaoAutorizadoExeption();
        }
        if(!pedido.getEstabelecimento().equals(codigoAcessoEstabelecimento)){
            throw new CodigoAcessoInvalidException();
        }

        if(!estabelecimento.getCodigoAcesso().equals(codigoAcessoEstabelecimento)){
            throw new CodigoAcessoInvalidException();
        }





        pedido.setStatus("Pedido em preparo");

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

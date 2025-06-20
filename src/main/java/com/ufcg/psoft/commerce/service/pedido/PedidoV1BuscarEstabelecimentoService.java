package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentosDistintosException;
import com.ufcg.psoft.commerce.exception.PedidoNaoExisteException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoV1BuscarEstabelecimentoService implements PedidoBuscarEstabelecimentoService{
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
    @Override
    public PedidoResponseDTO buscar(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoExisteException());

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(() -> new EstabelecimentoNaoEncontradoException());

        if(!pedido.getEstabelecimento().equals(estabelecimento)){
            throw new EstabelecimentosDistintosException();
        }

        if(!pedido.getEstabelecimento().getCodigoAcesso().equals(estabelecimentoCodigoAcesso)){
            throw new CodigoAcessoInvalidException();
        }

        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .estabelecimento(pedido.getEstabelecimento())
                .preco(pedido.getPreco())
                .cliente(pedido.getCliente())
                .entregador(pedido.getEntregador())
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .pizzas(pedido.getPizzas())
                .build();
    }
}

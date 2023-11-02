package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.PedidoNaoExisteException;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

        Set<Entregador> entregadores = estabelecimento.getEntregadores();

        Entregador daVez = null;
        for (Entregador entregador : entregadores) {
            if (entregador.getDisponivel() == "true") {
                daVez = entregador; // Atribui o entregador à variável "daVez"
                break; // Sai do loop assim que encontrar um entregador disponível
            }
        }

        if(daVez != null){
            pedido.setEntregador(daVez);
            daVez.setStatus("Em entrega");
        }else{
            pedido.setStatus("Pedido pronto");
        }

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

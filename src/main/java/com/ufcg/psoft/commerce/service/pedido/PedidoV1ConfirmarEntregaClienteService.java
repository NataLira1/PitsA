package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.*;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1ConfirmarEntregaClienteService implements PedidoConfirmarEntregaClienteService{
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Override
    public PedidoResponseDTO confirmarEntrega(Long pedidoId, Long clienteId, String codigoAcessoCliente) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteNaoExisteException());

        if(!cliente.getCodigoAcesso().equals(codigoAcessoCliente)){
            throw  new CodigoAcessoInvalidException();
        }

        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(()->new PedidoNaoExisteException());

        if(!pedido.isStatusPagamento()){
            throw new PagamentoNaoAutorizadoExeption();
        }

        if(!pedido.getStatus().toUpperCase().equals("PEDIDO EM ROTA")){
            throw new PulandoEtapasExeption();
        }

        pedido.setStatus("Pedido entregue");

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

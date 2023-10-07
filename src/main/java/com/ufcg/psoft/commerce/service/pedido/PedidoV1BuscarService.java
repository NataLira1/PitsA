package com.ufcg.psoft.commerce.service.pedido;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.ClientesDistintosException;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.PedidoNaoExisteException;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoV1BuscarService implements PedidoBuscarService{

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public PedidoResponseDTO buscar(Long pedidoId, Long clienteId, String clienteCodigoAcesso) {
        //acho irrelevante passar o id do cliente
        Optional<Pedido> pedidoOp = pedidoRepository.findById(pedidoId);

        if(!pedidoOp.isPresent()){
            throw new PedidoNaoExisteException();
        }

        Pedido pedido = pedidoOp.get();

        Optional<Cliente> clienteOp = clienteRepository.findById(clienteId);

        Cliente cliente = clienteOp.get();

        if(!clienteOp.isPresent()){
            throw new ClienteNaoExisteException();
        }

        String codigoAcessoPedido = pedido.getCliente().getCodigoAcesso();

        if(!pedido.getCliente().equals(cliente)){
            throw new ClientesDistintosException();
        }

        if(!codigoAcessoPedido.equals(clienteCodigoAcesso)){
            throw new CodigoAcessoInvalidException();
        }



        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .preco(pedido.getPreco())
                .cliente(pedido.getCliente())
                .estabelecimento(pedido.getEstabelecimento())
                .entregador(pedido.getEntregador())
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .pizzas(pedido.getPizzas())
                .build();

    }
}

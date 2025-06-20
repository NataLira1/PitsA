package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.*;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1BuscarClienteEstabelecimentoService implements PedidoBuscarClienteEstabelecimentoService{
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public PedidoResponseDTO buscar(Long clienteId, Long estabelecimentoId, Long pedidoId, String clienteCodigoAcesso) {

        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoExisteException());

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(() -> new EstabelecimentoNaoEncontradoException());

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteNaoExisteException());

        if(!pedido.getCliente().equals(cliente)){
            throw new ClientesDistintosException();
        }

        if(!pedido.getCliente().getCodigoAcesso().equals(clienteCodigoAcesso)){
            throw new CodigoAcessoInvalidException();
        }

        if(!pedido.getEstabelecimento().equals(estabelecimento)){
            throw new EstabelecimentosDistintosException();
        }

        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .estabelecimento(pedido.getEstabelecimento())
                .preco(pedido.getPreco())
                .pizzas(pedido.getPizzas())
                .cliente(pedido.getCliente())
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .entregador(pedido.getEntregador())
                .build();
    }
}

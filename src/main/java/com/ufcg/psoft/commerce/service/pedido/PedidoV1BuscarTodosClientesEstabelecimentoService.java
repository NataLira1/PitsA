package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.PedidoNaoExisteException;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PedidoV1BuscarTodosClientesEstabelecimentoService implements PedidoBuscarTodosClientesEstabelecimentoService{

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<PedidoResponseDTO> buscarTodos(Long clienteId, Long estabelecimentoId, String clienteCodigoAcesso) {

        List<Pedido> todosPedidos = pedidoRepository.findAll();
        List<Pedido> todosPedidosClienteEstabelecimento = new ArrayList<>();

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(() -> new EstabelecimentoNaoEncontradoException());

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteNaoExisteException());

        if(!cliente.getCodigoAcesso().equals(clienteCodigoAcesso)){
            throw new CodigoAcessoInvalidException();
        }

        for(Pedido pedido : todosPedidos){
            if(pedido.getCliente().equals(cliente) && pedido.getEstabelecimento().equals(estabelecimento)){
                todosPedidosClienteEstabelecimento.add(pedido);
            }
        }

        List<PedidoResponseDTO> pedidoResponseDTOS = new ArrayList<>();

        for(Pedido p : todosPedidosClienteEstabelecimento){
            PedidoResponseDTO aux = PedidoResponseDTO.builder()
                    .id(p.getId())
                    .pizzas(p.getPizzas())
                    .enderecoEntrega(p.getEnderecoEntrega())
                    .entregador(p.getEntregador())
                    .estabelecimento(p.getEstabelecimento())
                    .preco(p.getPreco())
                    .cliente(p.getCliente())
                    .status(p.getStatus())
                    .build();
            pedidoResponseDTOS.add(aux);
        }

        Collections.sort(pedidoResponseDTOS);

        return pedidoResponseDTOS;

    }
}

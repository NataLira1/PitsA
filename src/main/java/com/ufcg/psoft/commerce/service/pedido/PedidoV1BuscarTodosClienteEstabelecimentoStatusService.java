package com.ufcg.psoft.commerce.service.pedido;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;

@Service
public class PedidoV1BuscarTodosClienteEstabelecimentoStatusService implements PedidoBuscarTodosClienteEstabelecimentoStatusService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    private ClienteRepository clienteRepository;



    @Override
    public List<PedidoResponseDTO> buscarTodos(Long clienteId, Long estabelecimentoId, String status, String clienteCodigoAcesso) {
        
        List<Pedido> todosPedidos = pedidoRepository.findAll();
        List<Pedido> todosPedidosClienteEstabelecimento = new ArrayList<>();

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(() -> new EstabelecimentoNaoEncontradoException());

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteNaoExisteException());

        if(!cliente.getCodigoAcesso().equals(clienteCodigoAcesso)){
            throw new CodigoAcessoInvalidException();
        }

        for(Pedido pedido : todosPedidos){
            if(pedido.getCliente().equals(cliente) && pedido.getEstabelecimento().equals(estabelecimento) && pedido.getStatus().equals(status)){
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
                    .build();
            pedidoResponseDTOS.add(aux);
        }

        return pedidoResponseDTOS;
    }
    
}

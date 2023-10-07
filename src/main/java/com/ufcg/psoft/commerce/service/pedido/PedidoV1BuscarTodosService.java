package com.ufcg.psoft.commerce.service.pedido;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.SemPedidosExeption;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoV1BuscarTodosService implements PedidoBuscarTudoService{
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<PedidoResponseDTO> BuscarTodos(String codigoAcessoPassado) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<Pedido> pedidosFiltrados = new ArrayList<>();

        for(Pedido pedido: pedidos){
            if(pedido.getCliente().getCodigoAcesso().equals(codigoAcessoPassado)){
                pedidosFiltrados.add(pedido);
            }
        }

        if(pedidosFiltrados.isEmpty()){
            throw new SemPedidosExeption();
        }

        List<PedidoResponseDTO> pedidosResponse = new ArrayList<>();

        for(Pedido p : pedidosFiltrados){
            PedidoResponseDTO aux = PedidoResponseDTO.builder()
                    .id(p.getId())
                    .preco(p.getPreco())
                    .pizzas(p.getPizzas())
                    .enderecoEntrega(p.getEnderecoEntrega())
                    .estabelecimento(p.getEstabelecimento())
                    .cliente(p.getCliente())
                    .entregador(p.getEntregador())
                    .build();
            pedidosResponse.add(aux);
        }

        return pedidosResponse;

    }

}

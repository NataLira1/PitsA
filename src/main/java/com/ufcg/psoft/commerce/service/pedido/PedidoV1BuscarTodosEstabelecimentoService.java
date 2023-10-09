package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoV1BuscarTodosEstabelecimentoService implements PedidoBuscarTudoEstabelecimentoService{
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
    @Override
    public List<PedidoResponseDTO> buscarTodos(Long idEstabelecimento, String codigoEstabelecimento) {
        List<Pedido> todosPedidos = pedidoRepository.findAll();
        List<Pedido> todosPedidoEstabelecimento = new ArrayList<>();

        Optional<Estabelecimento> estabelecimentoOp = estabelecimentoRepository.findById(idEstabelecimento);

        if(!estabelecimentoOp.isPresent()){
            throw new EstabelecimentoNaoEncontradoException();
        }

        Estabelecimento estabelecimento = estabelecimentoOp.get();

        if(!estabelecimento.getCodigoAcesso().equals(codigoEstabelecimento)){
            throw new CodigoAcessoInvalidException();
        }

        for(Pedido pedido : todosPedidos){
            if(pedido.getEstabelecimento().equals(estabelecimento)){
                todosPedidoEstabelecimento.add(pedido);
            }
        }

        List<PedidoResponseDTO> pedidoResponseDTOS = new ArrayList<>();

        for(Pedido p : todosPedidoEstabelecimento){
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

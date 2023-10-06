package com.ufcg.psoft.commerce.service.pedido;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
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

import jakarta.transaction.Transactional;

@Service
public class PedidoV1CriarService implements PedidoCriarService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    
    @Override
    @Transactional
    public PedidoResponseDTO criar(Long clienteId, String clienteCodigoAcesso, Long estabelecimentoId, PedidoPostPutRequestDTO pedidoPostPutRequestDTO) {

        Optional<Cliente> clienteOp = clienteRepository.findById(clienteId);

        if(!clienteOp.isPresent()){
            throw new ClienteNaoExisteException();
        }

        if(!clienteOp.get().getCodigoAcesso().equals(clienteCodigoAcesso)){
            throw new CodigoAcessoInvalidException();
        }

        Optional<Estabelecimento> estabelecimentoOp = estabelecimentoRepository.findById(estabelecimentoId);

        if(!estabelecimentoOp.isPresent()){
            throw new EstabelecimentoNaoEncontradoException();
        }

        Pedido pedido = Pedido.builder()
                .preco(pedidoPostPutRequestDTO.getPreco())
                .clienteId(pedidoPostPutRequestDTO.getClienteId()) //analisar
                .enderecoEntrega(pedidoPostPutRequestDTO.getEnderecoEntrega())
                .estabelecimentoId(pedidoPostPutRequestDTO.getEstabelecimentoId()) //analisar
                .pizzas(pedidoPostPutRequestDTO.getPizzas())
                .build();

        pedidoRepository.save(pedido);

        return PedidoResponseDTO.builder()
                .preco(pedido.getPreco())
                .clienteId(pedido.getClienteId())
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .estabelecimentoId(pedido.getEstabelecimentoId())
                .pizzas(pedido.getPizzas())
                .build();
    }
}

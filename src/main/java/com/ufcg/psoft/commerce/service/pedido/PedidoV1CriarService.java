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
import com.ufcg.psoft.commerce.models.Pizza;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;

@Service
public class PedidoV1CriarService implements PedidoCriarService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    
    @Override
    public PedidoResponseDTO criar(String clienteCodigoAcesso, PedidoPostPutRequestDTO pedidoPostPutRequestDTO) {

        Optional<Cliente> clienteOp = clienteRepository.findById(pedidoPostPutRequestDTO.getClienteId());

        if(!clienteOp.isPresent()){
            throw new ClienteNaoExisteException();
        }

        if(!clienteOp.get().getCodigoAcesso().equals(clienteCodigoAcesso)){
            throw new CodigoAcessoInvalidException();
        }

        Optional<Estabelecimento> estabelecimentoOp = estabelecimentoRepository.findById(pedidoPostPutRequestDTO.getEstabelecimentoId());

        if(!estabelecimentoOp.isPresent()){
            throw new EstabelecimentoNaoEncontradoException();
        }
        
        Cliente cliente = clienteOp.get();
        Estabelecimento estabelecimento = estabelecimentoOp.get();
        
//        double precoPedido = getPreco
        
        Pedido pedido = Pedido.builder()
                .cliente(cliente) //analisar
                .enderecoEntrega(pedidoPostPutRequestDTO.getEnderecoEntrega())
                .estabelecimento(estabelecimento) //analisar
                .pizzas(pedidoPostPutRequestDTO.getPizzas())
                .build();
        pedido.setPreco(pedido.calcularPrecoPedido());
        
        for (Pizza p : pedido.getPizzas()) {
        	p.setPedido(pedido);
        }
        
        pedidoRepository.save(pedido);

        return PedidoResponseDTO.builder()
                .preco(pedido.getPreco())
                .cliente(pedido.getCliente())
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .estabelecimento(pedido.getEstabelecimento())
                .pizzas(pedido.getPizzas())
                .build();
    }
}
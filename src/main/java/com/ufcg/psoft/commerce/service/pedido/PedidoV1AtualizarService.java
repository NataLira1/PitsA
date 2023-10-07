package com.ufcg.psoft.commerce.service.pedido;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.PedidoNaoExisteException;
import com.ufcg.psoft.commerce.exception.SaborNaoDisponivelException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.models.Pizza;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;

@Service
public class PedidoV1AtualizarService implements PedidoAtualizarService{
    
    @Autowired
    PedidoRepository pedidoRepository;


    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public PedidoResponseDTO atualizar(Long pedidoId, String clienteCodigoAcesso,
            PedidoPutRequestDTO pedidoPutRequestDTO) {
        
        Optional<Pedido> pedidoOp = pedidoRepository.findById(pedidoId);

        if(!pedidoOp.isPresent()){
            throw new PedidoNaoExisteException();
        }

        Pedido pedido = pedidoOp.get();

        String codigoAcesso = pedido.getCliente().getCodigoAcesso();

        if(!codigoAcesso.equals(clienteCodigoAcesso)){
            throw new CodigoAcessoInvalidException();
        }

        Estabelecimento estabelecimento = pedido.getEstabelecimento();
        
        for(Pizza pizza : pedido.getPizzas()){
            if(pizza.getTamanho().toUpperCase().equals("GRANDE")){
                if(!estabelecimento.saboresDisponiveis().contains(pizza.getSabor1()) || !estabelecimento.saboresDisponiveis().contains(pizza.getSabor2())){
                    throw new SaborNaoDisponivelException();
                }
            }
            else{
                if(!estabelecimento.saboresDisponiveis().contains(pizza.getSabor1())){
                    throw new SaborNaoDisponivelException();
                }
            }
        }
        for (Pizza p : pedido.getPizzas()) {
        	p.setPedido(pedido);
        }
        
        pedido.setEnderecoEntrega(pedidoPutRequestDTO.getEnderecoEntrega());
        pedido.setPizzas(pedidoPutRequestDTO.getPizzas());
        pedido.setPreco(pedido.calcularPrecoPedido());

        pedido = pedidoRepository.save(pedido);

        return PedidoResponseDTO.builder()
                .id(pedidoId)
                .preco(pedido.getPreco())
                .cliente(pedido.getCliente())
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .estabelecimento(pedido.getEstabelecimento())
                .pizzas(pedido.getPizzas())
                .build();





        
    }

    
}

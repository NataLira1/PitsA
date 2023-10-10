package com.ufcg.psoft.commerce.service.pedido;

import java.util.Optional;

import com.ufcg.psoft.commerce.service.pagamento.DescontoDeciderService;
import com.ufcg.psoft.commerce.service.pagamento.DescontoService;
import com.ufcg.psoft.commerce.util.TipoPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.SaborNaoDisponivelException;
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

    @Autowired
    DescontoDeciderService descontoDeciderService;
    
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
                .estabelecimento(estabelecimento) //analisar
                .pizzas(pedidoPostPutRequestDTO.getPizzas())
                .status("Pedido recebido")
                //.formaDePagamento(pedidoPostPutRequestDTO.getFormaDePagamento())
                .statusPagamento(false)
                .build();

        pedido.setPreco(pedido.calcularPrecoPedido());

//        TipoPagamento tipoPagamento = pedidoPostPutRequestDTO.getFormaDePagamento().getTipo();
//        DescontoService descontoService = descontoDeciderService.desconto(tipoPagamento);
//
//        double desconto = descontoService.calcularDesconto(pedido.calcularPrecoPedido());
//
//        pedido.setPreco(pedido.calcularPrecoPedido() - desconto);
        
        if(pedidoPostPutRequestDTO.getEnderecoEntrega() != null){
            pedido.setEnderecoEntrega(pedidoPostPutRequestDTO.getEnderecoEntrega());
        }
        else{
            pedido.setEnderecoEntrega(cliente.getEndereco());
        }

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
        
        pedidoRepository.save(pedido);

        return PedidoResponseDTO.builder()
                .preco(pedido.getPreco())
                .cliente(pedido.getCliente())
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .estabelecimento(pedido.getEstabelecimento())
                .pizzas(pedido.getPizzas())
                .status(pedido.getStatus())
                //.formaDePagamento(pedido.getFormaDePagamento())
                .statusPagamento(pedido.isStatusPagamento())
                .build();
    }
}
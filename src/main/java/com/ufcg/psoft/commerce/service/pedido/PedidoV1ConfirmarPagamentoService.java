package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPutConfirmarPagamentoRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.FormaDePagamentoDiferenteException;
import com.ufcg.psoft.commerce.exception.PedidoNaoExisteException;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;
import com.ufcg.psoft.commerce.service.pagamento.DescontoDeciderService;
import com.ufcg.psoft.commerce.service.pagamento.DescontoService;
import com.ufcg.psoft.commerce.util.TipoPagamento;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1ConfirmarPagamentoService implements PedidoConfirmarPagamentoService{

    @Autowired
    private DescontoDeciderService descontoDeciderService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public PedidoResponseDTO confirmar(Long clienteId, String codigoAcessoCliente, Long pedidoId, PedidoPutConfirmarPagamentoRequestDTO pedidoPutConfirmarPagamentoRequestDTO) {

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(()-> new ClienteNaoExisteException());

        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(()->new PedidoNaoExisteException());

        if(!pedido.getCliente().getCodigoAcesso().equals(cliente.getCodigoAcesso())){
            throw new CodigoAcessoInvalidException();
        }
        pedido.setFormaDePagamento(pedidoPutConfirmarPagamentoRequestDTO.getFormaDePagamento());

        TipoPagamento tipoPagamento = pedidoPutConfirmarPagamentoRequestDTO.getFormaDePagamento().getTipo();
        DescontoService descontoService = descontoDeciderService.desconto(tipoPagamento);

        double desconto = descontoService.calcularDesconto(pedido.calcularPrecoPedido());

        pedido.setPreco(pedido.calcularPrecoPedido() - desconto);

        pedido.setStatusPagamento(true);

        return PedidoResponseDTO.builder()
                .preco(pedido.getPreco())
                .cliente(pedido.getCliente())
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .estabelecimento(pedido.getEstabelecimento())
                .pizzas(pedido.getPizzas())
                .status(pedido.getStatus())
                .formaDePagamento(pedido.getFormaDePagamento())
                .statusPagamento(pedido.isStatusPagamento())
                .build();
    }
}

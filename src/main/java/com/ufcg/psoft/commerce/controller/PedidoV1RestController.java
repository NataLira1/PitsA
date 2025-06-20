package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.service.pedido.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPutConfirmarPagamentoRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPutRequestDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoV1RestController {

        @Autowired
        private PedidoCriarService pedidoCriarService;

        @Autowired
        private PedidoAtualizarService pedidoAtualizarService;

        @Autowired
        private PedidoBuscarTudoService pedidoBuscarTudoService;

        @Autowired
        private PedidoBuscarService pedidoBuscarService;

        @Autowired
        private PedidoDeletarClienteService pedidoDeletarClienteService;

        @Autowired
        private PedidoDeletarEstabelecimentoService pedidoDeletarEstabelecimentoService;

        @Autowired
        private PedidoDeletarTodosClienteService pedidoDeletarTodosClienteService;



        @Autowired
        private PedidoDeletarTodosEstabelecimentoService pedidoDeletarTodosEstabelecimentoService;

        @Autowired
        private PedidoBuscarTodosClienteEstabelecimentoStatusService pedidoBuscarTodosClientesEstabelecimentoStatusService;

        @Autowired
        private PedidoConfirmarPreparoService pedidoConfirmarPreparoService;

        @Autowired
        private PedidoProntoService pedidoProntoService;
        @Autowired
        private PedidoAssociarEntregadorService pedidoAssociarEntregadorService;
        @Autowired
        private PedidoConfirmarEntregaClienteService pedidoConfirmarEntregaClienteService;


        @Autowired
        private PedidoConfirmarPagamentoService pedidoConfirmarPagamentoService;

        @Autowired
        private PedidoCancelarPedidoService pedidoCancelarPedidoService;

        @Autowired
        private PedidoBuscarTudoEstabelecimentoService pedidoBuscarTudoEstabelecimentoService;

        @Autowired
        private PedidoBuscarEstabelecimentoService pedidoBuscarEstabelecimentoService;
        
        @Autowired
        private PedidoBuscarClienteEstabelecimentoService pedidoBuscarClienteEstabelecimentoService;
        
        @Autowired
        private PedidoBuscarTodosClientesEstabelecimentoService pedidoBuscarTodosClientesEstabelecimentoService;

        @PostMapping
        public ResponseEntity<?> criarPedido(
        //            @RequestParam @Valid  Long clienteId,
                @RequestParam @Valid String clienteCodigoAcesso,
        //            @RequestParam @Valid Long estabelecimentoId,
                @RequestBody @Valid PedidoPostPutRequestDTO pedidoPostPutRequestDTO){

                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(pedidoCriarService.criar(clienteCodigoAcesso, pedidoPostPutRequestDTO));
        }

        @PutMapping("/{pedidoId}")
        public ResponseEntity<?> atualizarPedido(
                @PathVariable("pedidoId") @Valid Long pedidoId,
                @RequestParam @Valid String clienteCodigoAcesso,
                @RequestBody @Valid PedidoPutRequestDTO pedidoPutRequestDTO){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(pedidoAtualizarService.atualizar(pedidoId, clienteCodigoAcesso, pedidoPutRequestDTO));
        }

        @PutMapping("/{clienteId}/confirmar-pagamento")
        public ResponseEntity<?> confirmarPagamento(
                @PathVariable("clienteId") @Valid Long clienteId,
                @RequestParam @Valid String codigoAcessoCliente,
                @RequestParam @Valid Long pedidoId,
                //@RequestParam @Valid String metodoPagamento,
                @RequestBody @Valid PedidoPutConfirmarPagamentoRequestDTO pedidoPutConfirmarPagamentoRequestDTO){

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(pedidoConfirmarPagamentoService.confirmar(clienteId, codigoAcessoCliente, pedidoId, pedidoPutConfirmarPagamentoRequestDTO));
        }

        @PutMapping("/{pedidoId}/{estabelecimentoId}/confirmar-preparo")
        public ResponseEntity<?> confirmarPreparo(
                @PathVariable("pedidoId") @Valid Long pedidoId,
                @PathVariable("estabelecimentoId") @Valid Long estabelecimentoId,
                @RequestParam @Valid String codigoAcessoEstabelecimento
        ){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(pedidoConfirmarPreparoService.confirmarPreparo(pedidoId, estabelecimentoId,codigoAcessoEstabelecimento));
        }

        @PutMapping("/{pedidoId}/{estabelecimentoId}/pedido-pronto")
        public ResponseEntity<?> pedidoPronto(
                @PathVariable("pedidoId") @Valid Long pedidoId,
                @PathVariable("estabelecimentoId") @Valid Long estabelecimentoId,
                @RequestParam @Valid String codigoAcessoEstabelecimento
        ){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(pedidoProntoService.finalizado(pedidoId, estabelecimentoId,codigoAcessoEstabelecimento));
        }

        @PutMapping("/{pedidoId}/{estabelecimentoId}/associar-pedido-entregador")
        public ResponseEntity<?> associarPedidoEntregador(
                @PathVariable("pedidoId") @Valid Long pedidoId,
                @PathVariable("estabelecimentoId") @Valid Long estabelecimentoId,
                @RequestParam @Valid String codigoAcessoEstabelecimento
        ){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(pedidoAssociarEntregadorService.associar(pedidoId, estabelecimentoId,codigoAcessoEstabelecimento));
        }

        @PutMapping("/{pedidoId}/{clienteId}/cliente-confirmar-entrega")
        public ResponseEntity<?> confirmarPedidoCliente(
                @PathVariable("pedidoId") @Valid Long pedidoId,
                @PathVariable("clienteId") @Valid Long clienteId,
                @RequestParam @Valid String codigoAcessoCliente
        ){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(pedidoConfirmarEntregaClienteService.confirmarEntrega(pedidoId, clienteId, codigoAcessoCliente));
        }




        @GetMapping("/cliente/")
        public ResponseEntity<?> getAllCliente(
                @RequestParam @Valid String clienteCodigoAcesso
        ){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(pedidoBuscarTudoService.BuscarTodos(clienteCodigoAcesso));
        }

        @GetMapping("/estabelecimento/{estabelecimentoId}")
        public ResponseEntity<?> getAllEstabelecimento(
                @PathVariable("estabelecimentoId") @Valid Long estabelecimentoId,
                @RequestParam @Valid String estabelecimentoCodigoAcesso
        ){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(pedidoBuscarTudoEstabelecimentoService.buscarTodos(estabelecimentoId, estabelecimentoCodigoAcesso));
        }

        @GetMapping("/{pedidoId}/cliente/{clienteId}")
        public ResponseEntity<?> getOne(
                @PathVariable("pedidoId") @Valid Long pedidoId,
                @PathVariable("clienteId") @Valid Long clienteId,
                @RequestParam @Valid String clienteCodigoAcesso
        ){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(pedidoBuscarService.buscar(pedidoId, clienteId, clienteCodigoAcesso));
        }

        @GetMapping("/{pedidoId}/estabelecimeto/{estabelecimentoId}")
        public ResponseEntity<?> getOneEstabelecimento(
                @PathVariable("pedidoId") @Valid Long pedidoId,
                @PathVariable("estabelecimentoId") @Valid Long estabelecimentoId,
                @RequestParam @Valid String estabelecimentoCodigoAcesso
        ){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(pedidoBuscarEstabelecimentoService.buscar(pedidoId, estabelecimentoId, estabelecimentoCodigoAcesso));
        }

        @GetMapping("/pedido-cliente-estabelecimento/{clienteId}/{estabelecimentoId}/{pedidoId}")
        public ResponseEntity<?> getOnePedidoClienteEstabelecimento(
                @PathVariable("clienteId") @Valid Long clienteId,
                @PathVariable("estabelecimentoId") @Valid Long estabelecimentoId,
                @PathVariable("pedidoId") @Valid Long pedidoId,
                @RequestParam @Valid String clienteCodigoAcesso
        ){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(pedidoBuscarClienteEstabelecimentoService.buscar(clienteId, estabelecimentoId, pedidoId, clienteCodigoAcesso));
        }

        @GetMapping("/pedidos-cliente-estabelecimento/{clienteId}/{estabelecimentoId}")
        public ResponseEntity<?> getAllPedidosClientesEstabelecimento(
                @PathVariable("clienteId") @Valid Long clienteId,
                @PathVariable("estabelecimentoId") @Valid Long estabelecimentoId,
                @RequestParam @Valid String clienteCodigoAcesso
        ){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(pedidoBuscarTodosClientesEstabelecimentoService.buscarTodos(clienteId, estabelecimentoId, clienteCodigoAcesso));
        }

        @GetMapping("/pedidos-cliente-estabelecimento/{clienteId}/{estabelecimentoId}/{status}")
        public ResponseEntity<?> getAllPedidosClientesEstabelecimentoEntrega(
                @PathVariable("clienteId") @Valid Long clienteId,
                @PathVariable("estabelecimentoId") @Valid Long estabelecimentoId,
                @PathVariable("status") @Valid String status,
                @RequestParam @Valid String clienteCodigoAcesso
        ){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(pedidoBuscarTodosClientesEstabelecimentoStatusService.buscarTodos(
                                clienteId, estabelecimentoId, status, clienteCodigoAcesso
                        ));
        }



        @DeleteMapping("/{pedidoId}/cliente/{clienteId}")
        public ResponseEntity<?> deletarCliente(
                @PathVariable("pedidoId") @Valid Long pedidoId,
                @PathVariable("clienteId") @Valid Long clienteId,
                @RequestParam @Valid String clienteCodigoAcesso){
                pedidoDeletarClienteService.deletar(pedidoId, clienteId, clienteCodigoAcesso);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .build();
                }

        @DeleteMapping("/{pedidoId}/estabelecimento/{estabelecimentoId}")
        public ResponseEntity<?> deletarEstabelecimento(
        @PathVariable("pedidoId") @Valid Long pedidoId,
        @PathVariable("estabelecimentoId") @Valid Long estabelecimentoId,
        @RequestParam @Valid String estabelecimentoCodigoAcesso){
                pedidoDeletarEstabelecimentoService.deletar(pedidoId, estabelecimentoId, estabelecimentoCodigoAcesso);
                return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
        }
        
        @DeleteMapping("/cliente/{clienteId}")
        public ResponseEntity<?> deleteTodosCliente(
                @PathVariable("clienteId") @Valid Long clienteId,
                @RequestParam @Valid String clienteCodigoAcesso){
                pedidoDeletarTodosClienteService.deletarTodos(clienteId, clienteCodigoAcesso);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .build();
                }

        @DeleteMapping("/estabelecimento/{estabelecimentoId}")
        public ResponseEntity<?> deleteTodosEstabelecimento(
        @PathVariable("estabelecimentoId") @Valid Long estabelecimentoId,
        @RequestParam @Valid String estabelecimentoCodigoAcesso){
                pedidoDeletarTodosEstabelecimentoService.deletarTodos(estabelecimentoId, estabelecimentoCodigoAcesso);
                return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
        }

        @DeleteMapping("/{pedidoId}/cancelar-pedido")
        public ResponseEntity<?> cancelarPedido(
                @PathVariable("pedidoId") @Valid Long pedidoId,
                @RequestParam @Valid String clienteCodigoAcesso){
                        pedidoCancelarPedidoService.cancelar(pedidoId, clienteCodigoAcesso);
                        return ResponseEntity
                                .status(HttpStatus.NO_CONTENT)
                                .build();
                }
}
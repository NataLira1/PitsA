package com.ufcg.psoft.commerce.controller;

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
import com.ufcg.psoft.commerce.dto.pedido.PedidoPutRequestDTO;
import com.ufcg.psoft.commerce.service.pedido.PedidoAtualizarService;
import com.ufcg.psoft.commerce.service.pedido.PedidoBuscarService;
import com.ufcg.psoft.commerce.service.pedido.PedidoBuscarTudoService;
import com.ufcg.psoft.commerce.service.pedido.PedidoCriarService;
import com.ufcg.psoft.commerce.service.pedido.PedidoDeletarPedidoUnicoService;
import com.ufcg.psoft.commerce.service.pedido.PedidoDeletarTodosService;

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
    private PedidoDeletarPedidoUnicoService pedidoDeletarPedidoUnicoService;

    @Autowired
    private PedidoDeletarTodosService pedidoDeletarTodosService;



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

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam @Valid String clienteCodigoAcesso
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidoBuscarTudoService.BuscarTodos(clienteCodigoAcesso));
    }

//    @GetMapping
//    public ResponseEntity<?> getAllEstabelecimento(
//            @RequestParam @Valid String estabelecimentoCodigoAcesso
//    ){
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(pedidoBuscarTudoService.BuscarTodos(estabelecimentoCodigoAcesso));
//    }

    @GetMapping("/{pedidoId}/{clienteId}")
    public ResponseEntity<?> getOne(
            @PathVariable("pedidoId") @Valid Long pedidoId,
            @PathVariable("clienteId") @Valid Long clienteId,
            @RequestParam @Valid String clienteCodigoAcesso
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidoBuscarService.buscar(pedidoId, clienteId, clienteCodigoAcesso));
    }



    @DeleteMapping("/{pedidoId}/{clienteEstabelecimentoId}")
    public ResponseEntity<?> deletar(
        @PathVariable("pedidoId") @Valid Long pedidoId,
        @PathVariable("clienteEstabelecimentoId") @Valid Long clienteEstabelecimentoId,
        @RequestParam @Valid String codigoAcesso){
            pedidoDeletarPedidoUnicoService.deletar(pedidoId, clienteEstabelecimentoId, codigoAcesso);
            return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
        }
    
    @DeleteMapping("/{clienteEstabelecimentoId}")
    public ResponseEntity<?> deleteTodos(
        @PathVariable("clienteEstabelecimentoId") @Valid Long clienteEstabelecimentoId,
        @RequestParam @Valid String codigoAcesso){
            pedidoDeletarTodosService.deletarTodos(clienteEstabelecimentoId, codigoAcesso);
            return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
        }
}
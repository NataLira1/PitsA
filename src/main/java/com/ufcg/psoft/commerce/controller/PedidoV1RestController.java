package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.service.pedido.PedidoBuscarService;
import com.ufcg.psoft.commerce.service.pedido.PedidoBuscarTudoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPutRequestDTO;
import com.ufcg.psoft.commerce.service.pedido.PedidoAtualizarService;
import com.ufcg.psoft.commerce.service.pedido.PedidoCriarService;

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

}
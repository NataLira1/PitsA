package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.Cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.service.cliente.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteV1RestController {

    @Autowired
    ClienteCriarService clienteCriarService;

    @Autowired
    ClienteAtualizarService clienteAtualizarService;

    @Autowired
    ClienteBuscarService clienteBuscarService;

    @Autowired
    ClienteDeleteService clienteDeleteService;

    @Autowired
    ClienteInteresseService clienteInteresseService;

    @PostMapping
    public ResponseEntity<?> criarCliente(
            @RequestBody @Valid ClientePostPutRequestDTO clientePostPutRequestDTO
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                //.body(responseDTO.get());
                .body(clienteCriarService.criar(clientePostPutRequestDTO));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(
            @PathVariable("id") Long id,
            @RequestParam String codigoAcesso,
            @RequestParam String usuario,
            @RequestBody @Valid ClientePostPutRequestDTO clientePostPutRequestDTO
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteAtualizarService.atualizar(id, codigoAcesso, usuario ,clientePostPutRequestDTO));
    }

    @PutMapping("/{id}/demonstrarInteresse")
    public ResponseEntity<?> atualizarCliente(
            @PathVariable("id") Long id,
            @RequestParam String codigoAcesso,
            @RequestParam Long saborId
            ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteInteresseService.salvarInteresse(id, codigoAcesso, saborId));
    }

    @GetMapping
    public ResponseEntity<?> buscandoClientes(

    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteBuscarService.buscarTudo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscandoCliente(
            @PathVariable("id") Long id
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteBuscarService.buscar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluindoCliente(
            @PathVariable("id") Long id,
            @RequestParam String codigoAcesso,
            @RequestParam String usuario
    ){
        clienteDeleteService.excluir(id, codigoAcesso, usuario);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}

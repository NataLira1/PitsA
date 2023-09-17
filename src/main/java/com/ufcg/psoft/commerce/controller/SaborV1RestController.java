package com.ufcg.psoft.commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.service.sabor.SaborAtualizarService;
import com.ufcg.psoft.commerce.service.sabor.SaborCriarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/sabores", produces = MediaType.APPLICATION_JSON_VALUE)

public class SaborV1RestController {

    @Autowired
    private SaborCriarService saborCriarService;

    @Autowired
    private SaborAtualizarService saborAtualizarService;

    // @Autowired
    // private SaborBuscarService saborBuscarService;

    // @Autowired
    // private SaborBuscarTodosService saborBuscarTodosService;

    // @Autowired
    // private SaborDeletarService saborDeletarService;


    @PostMapping
    ResponseEntity<?> criarSabor(
            @RequestParam @Valid Long idEstabelecimento, @RequestParam @Valid String codigoDeAcesso,
            @RequestBody @Valid SaborPostPutRequestDTO saborPostPutRequestDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saborCriarService.criar(idEstabelecimento, codigoDeAcesso, saborPostPutRequestDTO));
    }

    @PutMapping("/{saborId}")
    public ResponseEntity<?> atualizarSabor(
            @RequestParam @Valid Long idEstabelecimento, @RequestParam @Valid String codigoDeAcesso,
            @PathVariable("saborId") @Valid Long saborId,
            @RequestBody @Valid SaborPostPutRequestDTO saborPostPutRequestDTO){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saborAtualizarService.atualizar(idEstabelecimento, codigoDeAcesso, saborId, saborPostPutRequestDTO));
    }
//
    // @GetMapping("/{saborId}")
    // public ResponseEntity<?> buscarSabor(
    //         @RequestParam @Valid Long idEstabelecimento, @RequestParam @Valid String codigoDeAcesso,
    //         @Valid @PathVariable("saborId") Long saborId){
    //     return ResponseEntity
    //             .status(HttpStatus.OK)
    //             .body(saborBuscarService.buscar(idEstabelecimento, codigoDeAcesso, saborId));
    // }
//
//    @GetMapping
//    ResponseEntity<?> buscarTodosSabores(@RequestParam @Valid Long idEstabelecimento, @RequestParam @Valid String codigoDeAcesso){
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(saborBuscarTodosService.buscarTodos(idEstabelecimento, codigoDeAcesso));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deletarSabor(@RequestParam @Valid Long idEstabelecimento, @RequestParam @Valid String codigoDeAcesso,
//                                          @Valid @PathVariable("saborId") Long saborId){
//        saborDeletarService.deletar(idEstabelecimento, codigoDeAcesso, saborId);
//        return ResponseEntity
//                .status(HttpStatus.NO_CONTENT)
//                .build();
//    }

}
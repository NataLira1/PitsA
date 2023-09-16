package com.ufcg.psoft.commerce.controller;


import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoPostPutRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/v1/estabelecimentos",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class EstabelecimentoController {

/*
    @Autowired
    private EstabelecimentoController estabelecimentoCriarService;

    @PostMapping
    ResponseEntity criar(
            @RequestBody @Valid EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO
            ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estabelecimentoCriarService.criar(estabelecimentoPostPutRequestDTO));
    }
*/

}

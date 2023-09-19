package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.service.associacao.EntregadorAssociaService;
import com.ufcg.psoft.commerce.service.associacao.EstabelecimentoAvaliaAssociacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/associacao", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssociacaoV1Controller {

    @Autowired
    private EntregadorAssociaService entregadorService;

    @Autowired
    private EstabelecimentoAvaliaAssociacaoService estabelecimentoAvaliaService;

    @PostMapping()
    public ResponseEntity<?> associaEntregadorAEstabelecimento(
            @RequestParam Long entregadorId,
            @RequestParam String codigoAcesso,
            @RequestParam Long estabelecimentoId
            ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entregadorService.associar(entregadorId, codigoAcesso, estabelecimentoId));
    }

    @PutMapping()
    public ResponseEntity<?> avaliaEntregador(
            @RequestParam Long entregadorId,
            @RequestParam Long estabelecimentoId,
            @RequestParam String codigoAcesso,
            boolean status) {
        estabelecimentoAvaliaService.avaliar(entregadorId, estabelecimentoId, codigoAcesso, status);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}

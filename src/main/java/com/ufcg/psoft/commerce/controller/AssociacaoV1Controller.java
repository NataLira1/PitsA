package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.service.associacao.EntregadorAssociaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/associacoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssociacaoV1Controller {

    @Autowired
    private EntregadorAssociaService entregadorService;

    @PostMapping("/associar/{entregadorId}")
    public ResponseEntity<Entregador> associarEntregadorAEstabelecimento(
            @PathVariable Long entregadorId,
            @RequestParam String codigoAcessoEntregador,
            @RequestParam String codigoAcessoEstabelecimento) {
        Entregador entregador = entregadorService.associar(entregadorId, codigoAcessoEntregador, codigoAcessoEstabelecimento);
        if (entregador != null) {
            return new ResponseEntity<>(entregador, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

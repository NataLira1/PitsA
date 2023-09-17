package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.exception.EntregadorNaoAssociadoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorNotFound;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.service.associacao.EntregadorAssociaService;
import com.ufcg.psoft.commerce.service.associacao.EstabelecimentoAprovaAssociacaoService;
import com.ufcg.psoft.commerce.service.associacao.EstabelecimentoRejeitaAssociacaoService;
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

    @Autowired
    private EstabelecimentoAprovaAssociacaoService estabelecimentoAprovaService;

    @Autowired
    private EstabelecimentoRejeitaAssociacaoService estabelecimentoRejeitaService;

    @PostMapping("/associar/{entregadorId}")
    public ResponseEntity<?> associaEntregadorAEstabelecimento(
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

    @PostMapping("associar/avaliar/{estabelecimentoId}")
    public ResponseEntity<String> avaliaEntregador(
            @PathVariable Long estabelecimentoId,
            @RequestParam Long entregadorId,
            @RequestParam String codigoAcessoEstabelecimento,
            @RequestParam boolean status) {
        try {
            if (status) {
                estabelecimentoAprovaService.aprovar(entregadorId, estabelecimentoId, codigoAcessoEstabelecimento);
                return new ResponseEntity<>("Entregador aprovado com sucesso.", HttpStatus.OK);
            } else {
                estabelecimentoRejeitaService.rejeitar(entregadorId, estabelecimentoId, codigoAcessoEstabelecimento);
                return new ResponseEntity<>("Entregador rejeitado com sucesso.", HttpStatus.OK);
            }
        } catch (EstabelecimentoNaoEncontradoException | EstabelecimentoCodigoAcessoInvalidoException |
                 EntregadorNotFound | EntregadorNaoAssociadoException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

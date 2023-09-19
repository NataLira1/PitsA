package com.ufcg.psoft.commerce.service.associacao;

import com.ufcg.psoft.commerce.exception.EntregadorCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EntregadorNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.models.Associacao;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.repositories.AssociacaoRepository;
import com.ufcg.psoft.commerce.repositories.EntregadorRepository;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorV1AssociaService implements EntregadorAssociaService {

    @Autowired
    private AssociacaoRepository associacaoRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public Associacao associar(Long entregadorId, String codigoAcessoEntregador, Long estabelecimentoId) {
        Entregador entregador = entregadorRepository.findById(entregadorId)
                .orElseThrow(EntregadorNaoEncontradoException::new);
        if (!entregador.getCodigoAcesso().equals(codigoAcessoEntregador)) {
            throw new EntregadorCodigoAcessoInvalidoException();
        }
        estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(EstabelecimentoNaoEncontradoException::new);
        Associacao associacao = Associacao.builder()
                .entregadorId(entregadorId)
                .codigoAcesso(codigoAcessoEntregador)
                .estabelecimentoId(estabelecimentoId)
                .build();
        entregador.setStatus("sob análise");
        entregadorRepository.save(entregador);
        return associacaoRepository.save(associacao);
    }


}

package com.ufcg.psoft.commerce.service.associacao;

import com.ufcg.psoft.commerce.exception.EntregadorCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorNotFound;
import com.ufcg.psoft.commerce.models.Associacao;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.repositories.AssociacaoRepository;
import com.ufcg.psoft.commerce.repositories.EntregadorRepository;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
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
    public Entregador associar(Long entregadorId, String codigoAcessoEntregador, String codigoAcessoEstabelecimento) {
        Entregador entregador = entregadorRepository.findById(entregadorId).orElseThrow(EntregadorNotFound::new);
        if (!codigoAcessoEntregador.equals(entregador.getCodigoAcesso())) {
            throw new EntregadorCodigoAcessoInvalidoException();
        }
        Estabelecimento estabelecimento = estabelecimentoRepository.findByCodigoAcesso(codigoAcessoEstabelecimento);
        if (!codigoAcessoEstabelecimento.equals(estabelecimento.getCodigoAcesso())) {
            throw new EstabelecimentoCodigoAcessoInvalidoException();
        }
        Associacao associacao = new Associacao();
        associacao.setEntregadorId(entregador.getId());
        associacao.setEstabelecimentoId(estabelecimento.getId());
        associacaoRepository.save(associacao);
        entregador.setStatus("sob análise");
        entregadorRepository.save(entregador);
        return entregador;
    }
}

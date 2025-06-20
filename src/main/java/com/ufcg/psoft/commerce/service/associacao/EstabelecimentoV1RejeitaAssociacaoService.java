package com.ufcg.psoft.commerce.service.associacao;

import com.ufcg.psoft.commerce.exception.EntregadorNaoAssociadoException;
import com.ufcg.psoft.commerce.exception.EntregadorNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.models.Associacao;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.repositories.AssociacaoRepository;
import com.ufcg.psoft.commerce.repositories.EntregadorRepository;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1RejeitaAssociacaoService implements EstabelecimentoRejeitaAssociacaoService {

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    AssociacaoRepository associacaoRepository;


    @Override
    public Associacao rejeitar(Long entregadorId, Long estabelecimentoId, String codigoAcessoEstabelecimento) {
        Entregador entregador = entregadorRepository.findById(entregadorId)
                .orElseThrow(EntregadorNaoEncontradoException::new);

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(EstabelecimentoNaoEncontradoException::new);

        if (!estabelecimento.getCodigoAcesso().equals(codigoAcessoEstabelecimento)) {
            throw new EstabelecimentoCodigoAcessoInvalidoException();
        }

        Associacao associacao = associacaoRepository.findByEntregadorIdAndEstabelecimentoId(entregadorId, estabelecimentoId);
        if (associacao == null) {
            throw new EntregadorNaoAssociadoException();
        }
        associacaoRepository.delete(associacao);
        return associacao;
    }
}

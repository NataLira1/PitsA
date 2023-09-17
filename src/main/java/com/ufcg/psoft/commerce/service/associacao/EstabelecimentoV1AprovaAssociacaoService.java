package com.ufcg.psoft.commerce.service.associacao;

import com.ufcg.psoft.commerce.exception.EntregadorNaoAssociadoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorNotFound;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.repositories.EntregadorRepository;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EstabelecimentoV1AprovaAssociacaoService implements EstabelecimentoAprovaAssociacaoService {

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public void aprovar(Long entregadorId, Long estabelecimentoId, String codigoAcessoEstabelecimento) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(EstabelecimentoNaoEncontradoException::new);
        if (!codigoAcessoEstabelecimento.equals(estabelecimento.getCodigoAcesso())) {
            throw new EstabelecimentoCodigoAcessoInvalidoException();
        }
        Entregador entregador = entregadorRepository.findById(entregadorId)
                .orElseThrow(EntregadorNotFound::new);
        if (!estabelecimento.getEntregadores().contains(entregador)) {
            throw new EntregadorNaoAssociadoException();
        }
        entregador.setStatus("aprovado");
        entregadorRepository.save(entregador);
        estabelecimento.getEntregadores().add(entregador);
        estabelecimentoRepository.save(estabelecimento);
    }


}

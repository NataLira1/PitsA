package com.ufcg.psoft.commerce.service.associacao;

import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.repositories.AssociacaoRepository;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntregadorV1AssociaService {

    @Autowired
    private AssociacaoRepository associacaoRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    public Entregador associarEntregadorAEstabelecimento(Long entregadorId, String codigoAcessoEstabelecimento) {
        Optional<Entregador> optionalEntregador = entregadorRepository.findById(entregadorId);
        if (optionalEntregador.isPresent()) {
            Entregador entregador = optionalEntregador.get();
            Estabelecimento estabelecimento = estabelecimentoRepository.findByCodigoAcesso(codigoAcessoEstabelecimento);
            if (estabelecimento.getCodigoAcesso() != codigoAcessoEstabelecimento) {
                throw new EstabelecimentoCodigoAcessoInvalidoException();
            }
            entregador.setStatus("Sob Análise");
            return associacaoRepository.save(entregador);
        }
        return null;
    }
}

package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Sabor;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EstabelecimentoV1BuscarCardapioService implements EstabelecimentoBuscarCardapioService{


    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public Set<Sabor> getCardapio(Long id, String codigo) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);

        if(codigo != es.getCodigoAcesso()) throw new EstabelecimentoCodigoAcessoInvalidoException();

        return es.getCardapio();
    }

    @Override
    public Set<Sabor> getCardapioPorTipo(Long id, String codigo, String tipo) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);

        if(codigo != es.getCodigoAcesso()) throw new EstabelecimentoCodigoAcessoInvalidoException();

        Stream<Sabor> cardapioFiltrado = es.getCardapio().stream().filter(
                item -> item.getTipo() == tipo
        );

        return cardapioFiltrado.collect(Collectors.toSet());
    }
}

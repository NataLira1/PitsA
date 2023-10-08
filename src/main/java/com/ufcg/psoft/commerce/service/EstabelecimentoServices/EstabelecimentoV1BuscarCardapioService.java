package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.TipoInexistenteException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Sabor;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;

@Service
public class EstabelecimentoV1BuscarCardapioService implements EstabelecimentoBuscarCardapioService{


    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public Set<Sabor> getCardapio(Long id) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);

        Set<Sabor> disponiveis = es.getCardapio().stream().filter(
                (sabor) -> sabor.isDisponivel()
        ).collect(Collectors.toSet());

        Set<Sabor> indisponiveis = es.getCardapio().stream().filter(
                (sabor) -> !sabor.isDisponivel()
        ).collect(Collectors.toSet());

        Set<Sabor> cardapioOrdenado = new LinkedHashSet<>();

        cardapioOrdenado.addAll(disponiveis);
        cardapioOrdenado.addAll(indisponiveis);

        return cardapioOrdenado;
    }

    @Override
    public Set<Sabor> getCardapioPorTipo(Long id, String tipo) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);


        if(!tipo.equalsIgnoreCase("SALGADO") && !tipo.equalsIgnoreCase("DOCE")){
            throw new TipoInexistenteException();
        }

        Stream<Sabor> cardapioFiltrado = es.getCardapio().stream().filter(
                item -> item.getTipo().equalsIgnoreCase(tipo)
        );

        return cardapioFiltrado.collect(Collectors.toSet());
    }

    @Override
    public Set<Sabor> getCardapioPorDisponibilidade(Long id,  Boolean disponivel) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);


        Stream<Sabor> cardapioFiltrado = es.getCardapio().stream().filter(
                item -> item.isDisponivel() == disponivel
        );

        return cardapioFiltrado.collect(Collectors.toSet());
    }
}

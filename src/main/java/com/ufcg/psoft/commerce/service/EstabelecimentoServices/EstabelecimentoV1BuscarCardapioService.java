package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import java.util.Set;
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
    public Set<Sabor> getCardapio(Long id, String codigo) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);

        if(!codigo.equals(es.getCodigoAcesso())) throw new EstabelecimentoCodigoAcessoInvalidoException();

        return es.getCardapio();
    }

    @Override
    public Set<Sabor> getCardapioPorTipo(Long id, String codigo, String tipo) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);

        if(!codigo.equals(es.getCodigoAcesso())) throw new EstabelecimentoCodigoAcessoInvalidoException();

        if(!tipo.toUpperCase().equals("SALGADO") && !tipo.toUpperCase().equals("DOCE")){
            throw new TipoInexistenteException();
        }

        Stream<Sabor> cardapioFiltrado = es.getCardapio().stream().filter(
                item -> item.getTipo() == tipo
        );

        return cardapioFiltrado.collect(Collectors.toSet());
    }

    @Override
    public Set<Sabor> getCardapioPorDisponibilidade(Long id, String codigo, Boolean disponivel) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);

        if(!codigo.equals(es.getCodigoAcesso())) throw new EstabelecimentoCodigoAcessoInvalidoException();


        Stream<Sabor> cardapioFiltrado = es.getCardapio().stream().filter(
                item -> item.isDisponivel() == disponivel
        );

        return cardapioFiltrado.collect(Collectors.toSet());
    }
}

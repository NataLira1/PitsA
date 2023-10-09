package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import java.util.*;
import java.util.stream.Collectors;

import com.ufcg.psoft.commerce.dto.sabor.SaborInteresseResponseDTO;
import com.ufcg.psoft.commerce.utils.ConvertToCardapioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.TipoInexistenteException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Sabor;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;

@Service
public class EstabelecimentoV1BuscarCardapioService implements EstabelecimentoBuscarCardapioService{


    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ConvertToCardapioDTO convertToCardapioDTO;

    @Override
    public Set<SaborInteresseResponseDTO> getCardapio(Long id) {
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

        return convertToCardapioDTO.convertToDTO(cardapioOrdenado);
    }

    @Override
    public Set<SaborInteresseResponseDTO> getCardapioPorTipo(Long id, String tipo) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);

        if(!tipo.equalsIgnoreCase("SALGADO") && !tipo.equalsIgnoreCase("DOCE")){
            throw new TipoInexistenteException();
        }

        Set<Sabor> cardapioFiltrado = es.getCardapio().stream().filter(
                item -> item.getTipo().equalsIgnoreCase(tipo)
        ).collect(Collectors.toSet());


        return convertToCardapioDTO.convertToDTO(cardapioFiltrado);
    }

    @Override
    public Set<SaborInteresseResponseDTO> getCardapioPorDisponibilidade(Long id, Boolean disponivel) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);


        Set<Sabor> cardapioFiltrado = es.getCardapio().stream().filter(
                item -> item.isDisponivel() == disponivel
        ).collect(Collectors.toSet());

        return convertToCardapioDTO.convertToDTO(cardapioFiltrado);
    }
}

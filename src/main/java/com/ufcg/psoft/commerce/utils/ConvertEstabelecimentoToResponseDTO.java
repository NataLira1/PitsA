package com.ufcg.psoft.commerce.utils;

import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoResponseGetDTO;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConvertEstabelecimentoToResponseDTO {

    @Autowired
    ConvertToCardapioDTO convertToCardapioDTO;

    public List<EstabelecimentoResponseGetDTO> convertToDTO(List<Estabelecimento> estabelecimentos){
        List<EstabelecimentoResponseGetDTO> estabelecimentoDTOS = new ArrayList<>();
        estabelecimentos.forEach(
                estabelecimento -> estabelecimentoDTOS.add(
                        EstabelecimentoResponseGetDTO.builder()
                                .usuario(estabelecimento.getUsuario())
                                .codigoAcesso(estabelecimento.getCodigoAcesso())
                                .entregadores(estabelecimento.getEntregadores())
                                .cardapio(convertToCardapioDTO.convertToDTO(estabelecimento.getCardapio()))
                                .build()
                )
        );
        return estabelecimentoDTOS;
    }

    public EstabelecimentoResponseGetDTO convertToDTO(Estabelecimento estabelecimento){
        EstabelecimentoResponseGetDTO estabelecimentoDTOS = EstabelecimentoResponseGetDTO.builder()
                .codigoAcesso(estabelecimento.getCodigoAcesso())
                .usuario(estabelecimento.getUsuario())
                .entregadores(estabelecimento.getEntregadores())
                .cardapio(convertToCardapioDTO.convertToDTO(estabelecimento.getCardapio()))
                .build();


        return estabelecimentoDTOS;
    }

}

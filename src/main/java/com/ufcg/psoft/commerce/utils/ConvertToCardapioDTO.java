package com.ufcg.psoft.commerce.utils;

import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborInteresseResponseDTO;
import com.ufcg.psoft.commerce.models.Sabor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Service
public class ConvertToCardapioDTO {


    public Set<SaborInteresseResponseDTO> convertToDTO(Set<Sabor> sabores){
        List<Sabor> cardapioMid = sabores.stream().toList();

        Set<SaborInteresseResponseDTO> cardapioFinal = new LinkedHashSet<>();
        for (int i = 0; i < cardapioMid.size(); i++) {
            Sabor curr = cardapioMid.get(i);
            SaborInteresseResponseDTO saborInteresseResponseDTO = SaborInteresseResponseDTO
                    .builder()
                    .id(curr.getId())
                    .valorMedia(curr.getValorMedia())
                    .valorGrande(curr.getValorGrande())
                    .nome(curr.getNome())
                    .tipo(curr.getTipo())
                    .disponivel(curr.isDisponivel())
                    .build();
            cardapioFinal.add(saborInteresseResponseDTO);
        }

        List<SaborInteresseResponseDTO> saborInteresseResponseDTOS = cardapioFinal.stream().toList();
        for (int i = 0; i < cardapioMid.size(); i++) {
            Set<ClienteResponseDTO> clienteResponseDTOS = new LinkedHashSet<>();
            Sabor curr = cardapioMid.get(i);
            if(curr.getClientesInteressados() != null){
                curr.getClientesInteressados().forEach(
                        (cl) -> clienteResponseDTOS.add(ClienteResponseDTO.builder()
                                .nomeCompleto(cl.getNomeCompleto())
                                .usuario(cl.getUsuario())
                                .endereco(cl.getEndereco())
                                .id(cl.getId())
                                .build())
                );
            }
            saborInteresseResponseDTOS.get(i).setClientesInteressados(clienteResponseDTOS);
        }

        Set<SaborInteresseResponseDTO> cardapioRetorno = new LinkedHashSet<>();
        cardapioRetorno.addAll(saborInteresseResponseDTOS);


        return cardapioRetorno;
    }

}

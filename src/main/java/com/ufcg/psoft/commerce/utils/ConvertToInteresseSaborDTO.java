package com.ufcg.psoft.commerce.utils;

import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborInteresseResponseDTO;
import com.ufcg.psoft.commerce.models.Sabor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class ConvertToInteresseSaborDTO {

    public SaborInteresseResponseDTO convertToDTO(Sabor sabor){
        Set<ClienteResponseDTO> clienteInteressados = new HashSet<>();

        sabor.getClientesInteressados().forEach(
                (cl) -> clienteInteressados.add(ClienteResponseDTO.builder()
                        .nomeCompleto(cl.getNomeCompleto())
                        .endereco(cl.getEndereco())
                        .usuario(cl.getUsuario())
                        .id(cl.getId())
                        .build())
        );

        SaborInteresseResponseDTO saborInteresseResponseDTO = SaborInteresseResponseDTO.
                builder()
                .tipo(sabor.getTipo())
                .valorMedia(sabor.getValorMedia())
                .valorGrande(sabor.getValorGrande())
                .nome(sabor.getNome())
                .disponivel(sabor.isDisponivel())
                .clientesInteressados(clienteInteressados)
                .id(sabor.getId())
                .build();

        return saborInteresseResponseDTO;
    }
}

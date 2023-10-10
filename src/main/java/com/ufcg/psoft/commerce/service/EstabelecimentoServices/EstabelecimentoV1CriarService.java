package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoPostResponseDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1CriarService implements EstabelecimentoCriarService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EstabelecimentoPostResponseDTO criar(EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.save(
                modelMapper.map(estabelecimentoPostPutRequestDTO, Estabelecimento.class)
        );

        if(estabelecimentoPostPutRequestDTO.getCodigoAcesso().length() < 6) throw new EstabelecimentoCodigoAcessoInvalidoException();

        EstabelecimentoPostResponseDTO retorno = EstabelecimentoPostResponseDTO.builder()
                .usuario(estabelecimento.getUsuario())
                .id(estabelecimento.getId())
                .codigoAcesso(estabelecimento.getCodigoAcesso())
                .build();

        return retorno;
    }
}

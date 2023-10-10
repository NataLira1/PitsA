package com.ufcg.psoft.commerce.service.EstabelecimentoServices;


import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoResponseGetDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.utils.ConvertEstabelecimentoToResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstabelecimentoV1BuscarService implements EstabelecimentoBuscarService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ConvertEstabelecimentoToResponseDTO convertEstabelecimentoToResponseDTO;

    @Override
    public List<EstabelecimentoResponseGetDTO> getAll() {

        return convertEstabelecimentoToResponseDTO.convertToDTO(estabelecimentoRepository.findAll());
    }

    @Override
    public EstabelecimentoResponseGetDTO getOne(Long id) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);

        return convertEstabelecimentoToResponseDTO.convertToDTO(es);
    }
}

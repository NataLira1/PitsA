package com.ufcg.psoft.commerce.service.sabor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.SaborNaoExisteException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Sabor;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.SaborRepository;

@Service
public class SaborV1BuscarService implements SaborBuscarService {

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;


    @Override
    public SaborResponseDTO buscar(Long idEstabelecimento, String codigoAcesso, Long id) {
    
        Optional<Estabelecimento> estabelecimentoOp = estabelecimentoRepository.findById(idEstabelecimento);

        if(!estabelecimentoOp.isPresent()){
            throw new EstabelecimentoNaoEncontradoException();
        }
        
        Estabelecimento estabelecimento = estabelecimentoOp.get();
        
        if(!estabelecimento.getCodigoAcesso().equals(codigoAcesso)){
            throw new EstabelecimentoCodigoAcessoInvalidoException();
        }

        if(saborRepository.findById(id).isPresent()){
            Sabor sabor = saborRepository.findById(id).get();
            return SaborResponseDTO.builder()
                .id(sabor.getId())
                .nome(sabor.getNome())
                .tipo(sabor.getTipo())
                .valorMedia(sabor.getValorMedia())
                .valorGrande(sabor.getValorGrande())
                .disponivel(sabor.isDisponivel())
                .build();
        }
        else{
            throw new SaborNaoExisteException();
        }
    }
}
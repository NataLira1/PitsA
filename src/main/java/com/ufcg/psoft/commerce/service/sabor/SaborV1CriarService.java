package com.ufcg.psoft.commerce.service.sabor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.sabor.SaborPutRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.TipoInexistenteException;
import com.ufcg.psoft.commerce.exception.ValorSaborInvalidoException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Sabor;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.SaborRepository;

@Service
public class SaborV1CriarService implements SaborCriarService{

    @Autowired
    private SaborRepository saborRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Override
    public SaborResponseDTO criar(Long idEstabelecimento, String codigoAcesso, SaborPutRequestDTO saborPutRequestDTO) {
        
        Optional<Estabelecimento> estabelecimentoOp = estabelecimentoRepository.findById(idEstabelecimento);

        if(!estabelecimentoOp.isPresent()){
            throw new EstabelecimentoNaoEncontradoException();
        }
        
        Estabelecimento estabelecimento = estabelecimentoOp.get();
        
        if(!estabelecimento.getCodigoAcesso().equals(codigoAcesso)){
            throw new EstabelecimentoCodigoAcessoInvalidoException();
        }
        if(!saborPutRequestDTO.getTipo().toUpperCase().equals("SALGADO") && !saborPutRequestDTO.getTipo().toUpperCase().equals("DOCE")){
            throw new TipoInexistenteException();
        }

        if(saborPutRequestDTO.getValorGrande()<=0 || saborPutRequestDTO.getValorMedia()<=0){
            throw new ValorSaborInvalidoException();
        }
        
        Sabor sabor =
        		Sabor.builder()
                        .nome(saborPutRequestDTO.getNome())
                        .tipo(saborPutRequestDTO.getTipo())
                        .valorMedia(saborPutRequestDTO.getValorMedia())
                        .valorGrande(saborPutRequestDTO.getValorGrande())
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build()
        ;
        
        saborRepository.save(sabor);

        return SaborResponseDTO.builder()
                .id(sabor.getId())
                .nome(sabor.getNome())
                .tipo(sabor.getTipo())
                .valorMedia(sabor.getValorMedia())
                .valorGrande(sabor.getValorGrande())
                .disponivel(sabor.isDisponivel())
                .build();
    }
}
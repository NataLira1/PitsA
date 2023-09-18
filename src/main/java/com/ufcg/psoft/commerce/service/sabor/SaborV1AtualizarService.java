package com.ufcg.psoft.commerce.service.sabor;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.SaborNaoExisteException;
import com.ufcg.psoft.commerce.exception.TipoInexistenteException;
import com.ufcg.psoft.commerce.exception.ValorSaborInvalidoException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Sabor;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.SaborRepository;

@Service
public class SaborV1AtualizarService implements SaborAtualizarService {

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ModelMapper modelMapper;



    @Override
    public SaborResponseDTO atualizar(Long idEstabelecimento, String codigoAcesso, Long id, SaborPostPutRequestDTO saborPostPutRequestDTO) {

        Optional<Estabelecimento> estabelecimentoOp = estabelecimentoRepository.findById(idEstabelecimento);

        if(!estabelecimentoOp.isPresent()){
            throw new EstabelecimentoNaoEncontradoException();
        }
        
        Estabelecimento estabelecimento = estabelecimentoOp.get();
        
        if(!estabelecimento.getCodigoAcesso().equals(codigoAcesso)){
            throw new EstabelecimentoCodigoAcessoInvalidoException();
        }

        if(!saborPostPutRequestDTO.getTipo().toUpperCase().equals("SALGADO") && !saborPostPutRequestDTO.getTipo().toUpperCase().equals("DOCE")){
            throw new TipoInexistenteException();
        }

        if(saborPostPutRequestDTO.getValorGrande()<=0 || saborPostPutRequestDTO.getValorMedia()<=0){
            throw new ValorSaborInvalidoException();
        }

        if (saborRepository.findById(id).isPresent()){
            Sabor saborAtualizado = modelMapper.map(saborPostPutRequestDTO, Sabor.class);
            saborAtualizado = saborRepository.save(saborAtualizado);
            return SaborResponseDTO.builder()
                .id(saborAtualizado.getId())
                .nome(saborAtualizado.getNome())
                .tipo(saborAtualizado.getTipo())
                .valorMedia(saborAtualizado.getValorMedia())
                .valorGrande(saborAtualizado.getValorGrande())
                .disponivel(saborAtualizado.isDisponivel())
                .build();
        }

        
        else{
            throw new SaborNaoExisteException();
        }
    }
}


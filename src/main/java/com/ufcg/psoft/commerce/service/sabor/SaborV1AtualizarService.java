package com.ufcg.psoft.commerce.service.sabor;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.sabor.SaborPutRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
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
    public SaborResponseDTO atualizar(Long idEstabelecimento, String codigoAcesso, Long id, SaborPutRequestDTO saborPutRequestDTO) {

        Optional<Sabor> saborOptional = saborRepository.findById(id);
        
        if (saborOptional.isPresent()) {
            Sabor saborExistente = saborOptional.get();
            

            Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(() -> new EstabelecimentoNaoEncontradoException());
            

            if(!estabelecimento.getCodigoAcesso().equals(codigoAcesso)){
                throw new CodigoAcessoInvalidException();
            }

            if(!saborPutRequestDTO.getTipo().toUpperCase().equals("SALGADO") && !saborPutRequestDTO.getTipo().toUpperCase().equals("DOCE")){
                throw new TipoInexistenteException();
            }
    
            if(saborPutRequestDTO.getValorGrande()<=0 || saborPutRequestDTO.getValorMedia()<=0){
                throw new ValorSaborInvalidoException();
            }
        
            saborExistente.setNome(saborPutRequestDTO.getNome());
            saborExistente.setTipo(saborPutRequestDTO.getTipo());
            saborExistente.setValorMedia(saborPutRequestDTO.getValorMedia());
            saborExistente.setValorGrande(saborPutRequestDTO.getValorGrande());
        
            saborExistente = saborRepository.save(saborExistente);
        
            return SaborResponseDTO.builder()
                    .id(saborExistente.getId())
                    .nome(saborExistente.getNome())
                    .tipo(saborExistente.getTipo())
                    .valorMedia(saborExistente.getValorMedia())
                    .valorGrande(saborExistente.getValorGrande())
                    .disponivel(saborExistente.isDisponivel())
                    .build();
        }
        else{
            throw new SaborNaoExisteException();
        }
}

}


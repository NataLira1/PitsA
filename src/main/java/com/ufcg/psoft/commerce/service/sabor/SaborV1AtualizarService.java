package com.ufcg.psoft.commerce.service.sabor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.SaborNaoExisteException;
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
    public Sabor atualizar(Long idEstabelecimento, String codigoAcesso, Long id, SaborPostPutRequestDTO saborPostPutRequestDTO) {

        if(!estabelecimentoRepository.findById(idEstabelecimento).isPresent()){
            throw new EstabelecimentoNaoEncontradoException();
        }
        if(estabelecimentoRepository.findById(idEstabelecimento).get().getCodigoAcesso() != codigoAcesso){
            throw new EstabelecimentoCodigoAcessoInvalidoException();
        }

        if (saborRepository.findById(id).isPresent()){
            Sabor saborAtualizado = modelMapper.map(saborPostPutRequestDTO, Sabor.class);
            return saborRepository.save(saborAtualizado);
        }
        else{
            throw new SaborNaoExisteException();
        }
    }
}

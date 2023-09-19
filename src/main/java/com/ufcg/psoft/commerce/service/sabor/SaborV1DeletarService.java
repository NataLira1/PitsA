package com.ufcg.psoft.commerce.service.sabor;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.SaborNaoExisteException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.SaborRepository;

@Service
public class SaborV1DeletarService implements SaborDeletarService {

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public void deletar(Long idEstabelecimento, String codigoAcesso, Long id){

        Optional<Estabelecimento> estabelecimentoOp = estabelecimentoRepository.findById(idEstabelecimento);

        if(!estabelecimentoOp.isPresent()){
            throw new EstabelecimentoNaoEncontradoException();
        }
        
        Estabelecimento estabelecimento = estabelecimentoOp.get();
        
        if(!estabelecimento.getCodigoAcesso().equals(codigoAcesso)){
            throw new EstabelecimentoCodigoAcessoInvalidoException();
        }

        if (saborRepository.findById(id).isPresent()){
            saborRepository.deleteById(id);
        }
        else{
            throw new SaborNaoExisteException();
        }
    }
}

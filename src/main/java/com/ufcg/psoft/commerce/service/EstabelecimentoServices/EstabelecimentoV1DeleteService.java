package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1DeleteService implements EstabelecimentoDeleteService{

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public void delete(Long id, String codigo) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);

        if(codigo == null || !codigo.equals(es.getCodigoAcesso())){
            throw new EstabelecimentoCodigoAcessoInvalidoException();
        }

        estabelecimentoRepository.deleteById(es.getId());
    }
}

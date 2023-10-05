package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoPatchCodigoDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1PatchCodigoService implements EstabelecimentoPatchCodigoService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public Estabelecimento atualizarEmail(Long id, String codigo, EstabelecimentoPatchCodigoDTO estabelecimentoPatchCodigoDTO) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);

        // VALIDAR O CODIGO DE ACESSO
        if(codigo == null || !codigo.equals(es.getCodigoAcesso())) throw new EstabelecimentoCodigoAcessoInvalidoException();

        es.setCodigoAcesso(estabelecimentoPatchCodigoDTO.getCodigo());
        estabelecimentoRepository.save(es);

        return es;
    }
}

package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import com.ufcg.psoft.commerce.dto.sabor.SaborPatchDisponibilidadeDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoBodyInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.SaborNaoExisteException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Sabor;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.SaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1PatchDisponibilidadeSaborService implements EstabelecimentoPatchDisponibilidadeSaborService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    SaborRepository saborRepository;


    @Override
    public Sabor alterarDisponibilidade(Long idEstabelecimento,
                                        Long idSabor,
                                        String codigoDeAcesso,
                                        SaborPatchDisponibilidadeDTO saborPatchDisponibilidadeDTO) {


            Estabelecimento es = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoEncontradoException::new);

            if(codigoDeAcesso == null || !codigoDeAcesso.equals(es.getCodigoAcesso())) throw new EstabelecimentoCodigoAcessoInvalidoException();

            Sabor sabor = es.getCardapio().stream().filter(
                    item -> item.getId() == idSabor
            ).findFirst().orElseThrow(SaborNaoExisteException::new);

            if(saborPatchDisponibilidadeDTO.getDisponibilidade() == null) throw new EstabelecimentoBodyInvalidoException();

            sabor.setDisponivel(saborPatchDisponibilidadeDTO.getDisponibilidade());
            saborRepository.save(sabor);

            return sabor;


    }
}

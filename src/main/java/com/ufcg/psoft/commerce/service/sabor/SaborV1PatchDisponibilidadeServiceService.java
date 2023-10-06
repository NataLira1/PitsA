package com.ufcg.psoft.commerce.service.sabor;

import com.ufcg.psoft.commerce.dto.sabor.SaborPatchDisponibilidadeDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoBodyInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.SaborNaoExisteException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Sabor;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.SaborRepository;
import com.ufcg.psoft.commerce.service.sabor.SaborPatchDisponibilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SaborV1PatchDisponibilidadeServiceService implements SaborPatchDisponibilidadeService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    SaborRepository saborRepository;


    @Override
    public Sabor alterarDisponibilidade(Long idEstabelecimento,
                                        Long idSabor,
                                        String codigoDeAcesso,
                                        SaborPatchDisponibilidadeDTO saborPatchDisponibilidadeDTO) {

        try{
            Estabelecimento es = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoEncontradoException::new);

            if(codigoDeAcesso == null || !codigoDeAcesso.equals(es.getCodigoAcesso())) throw new EstabelecimentoCodigoAcessoInvalidoException();

            Sabor sabor = es.getCardapio().stream().filter(
                    item -> item.getId() == idSabor
            ).findFirst().orElseThrow(SaborNaoExisteException::new);


            sabor.setDisponivel(saborPatchDisponibilidadeDTO.getDisponibilidade());
            saborRepository.save(sabor);

            return sabor;
        }catch (Exception e){
            throw new EstabelecimentoBodyInvalidoException();
        }

    }
}

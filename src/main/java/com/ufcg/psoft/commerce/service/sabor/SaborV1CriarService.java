package com.ufcg.psoft.commerce.service.sabor;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
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
    public Sabor criar(Long idEstabelecimento, String codigoAcesso, SaborPostPutRequestDTO saborPostPutRequestDTO) {
        

        if(!estabelecimentoRepository.findById(idEstabelecimento).isPresent()){
            throw new EstabelecimentoNaoEncontradoException();
        }
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).get();
        
        if(!estabelecimento.getCodigoAcesso().equals(codigoAcesso)){
            throw new EstabelecimentoCodigoAcessoInvalidoException();
        }

        Sabor sabor = saborRepository.save(
                Sabor.builder()
                        .nome(saborPostPutRequestDTO.getNome())
                        .tipo(saborPostPutRequestDTO.getTipo())
                        .precoM(saborPostPutRequestDTO.getPrecoM())
                        .precoG(saborPostPutRequestDTO.getPrecoG())
                        .disponivel(true)
                        .build()
        );

        sabor.setEstabelecimento(estabelecimento);
        Set<Sabor> novoCardapio = estabelecimento.getCardapio();
        novoCardapio.add(sabor);
        estabelecimento.setCardapio(novoCardapio);
        estabelecimentoRepository.save(estabelecimento);
        return sabor;
    }
}
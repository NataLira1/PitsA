package com.ufcg.psoft.commerce.service.EstabelecimentoServices;


import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1PutService implements EstabelecimentoPutService{

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Estabelecimento update(Long id, String codigo, EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);

        if(!codigo.equals(es.getCodigoAcesso())) throw new EstabelecimentoCodigoAcessoInvalidoException();

        es.setCardapio(estabelecimentoPostPutRequestDTO.getCardapio());
        es.setCodigoAcesso(estabelecimentoPostPutRequestDTO.getCodigoAcesso());
        es.setEntregadores(estabelecimentoPostPutRequestDTO.getEntregadores());
        es.setUsuario(estabelecimentoPostPutRequestDTO.getUsuario());

        return estabelecimentoRepository.save(es);
    }
}

package com.ufcg.psoft.commerce.service.EstabelecimentoServices;


import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstabelecimentoV1BuscarService implements EstabelecimentoBuscarService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public List<Estabelecimento> getAll() {
        return estabelecimentoRepository.findAll();
    }

    @Override
    public Estabelecimento getOne(Long id) {
        Estabelecimento es = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoEncontradoException::new);

        return es;
    }
}

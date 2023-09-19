package com.ufcg.psoft.commerce.service.sabor;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoEncontradoException;
import com.ufcg.psoft.commerce.models.Sabor;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.SaborRepository;

@Service
public class SaborV1BuscarTodosService implements SaborBuscarTodosService {


    @Autowired
    SaborRepository saborRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public List<SaborResponseDTO> buscarTodos(Long idEstabelecimento, String codigoAcesso) {
        if(!estabelecimentoRepository.findById(idEstabelecimento).isPresent()){
            throw new EstabelecimentoNaoEncontradoException();
        }
        if(estabelecimentoRepository.findById(idEstabelecimento).get().getCodigoAcesso() != codigoAcesso){
            throw new EstabelecimentoCodigoAcessoInvalidoException();
        }
        List<Sabor> sabores = saborRepository.findAll();
        List<SaborResponseDTO> saboresDTO = new ArrayList<SaborResponseDTO>();
        for (int i = 0; i<sabores.size(); i++){
            if(sabores.get(i).getEstabelecimento().getId().equals(idEstabelecimento)){
                saboresDTO.add(
                    SaborResponseDTO.builder()
                    .id(sabores.get(i).getId())
                    .nome(sabores.get(i).getNome())
                    .tipo(sabores.get(i).getTipo())
                    .valorMedia(sabores.get(i).getValorMedia())
                    .valorGrande(sabores.get(i).getValorGrande())
                    .disponivel(sabores.get(i).isDisponivel())
                    .build()
                );
            }
        }
        return saboresDTO;
    }
}

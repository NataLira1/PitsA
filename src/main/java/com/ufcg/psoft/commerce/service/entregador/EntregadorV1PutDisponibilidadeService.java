package com.ufcg.psoft.commerce.service.entregador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.Entregador.EntregadorDisponibilidadeDTO;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.exception.CodigoAcessoInvalidException;
import com.ufcg.psoft.commerce.exception.EntregadorNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorDisponibilidadeInvalida;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.repositories.EntregadorRepository;

@Service
public class EntregadorV1PutDisponibilidadeService implements EntregadorPutDisponibilidadeService{

    @Autowired
    EntregadorRepository entregadorRepository;


    @Override
    public EntregadorResponseDTO mudar(Long entregadorId, String codigoAcesso,
            EntregadorDisponibilidadeDTO entregadorDisponibilidadeDTO) {
        
            Entregador entregador = entregadorRepository.findById(entregadorId).orElseThrow(EntregadorNaoEncontradoException::new);

            if(!entregador.getCodigoAcesso().equals(codigoAcesso)){
                throw new CodigoAcessoInvalidException();
            }

            if(!entregadorDisponibilidadeDTO.getDisponibilidade().toUpperCase().equals("DESCANSO") && !entregadorDisponibilidadeDTO.getDisponibilidade().toUpperCase().equals("ATIVO")){
                throw new EntregadorDisponibilidadeInvalida();   
            }

            entregador.setDisponivel(entregadorDisponibilidadeDTO.getDisponibilidade());

            entregadorRepository.save(entregador);

            return EntregadorResponseDTO.builder()
                .nome(entregador.getNome())
                .id(entregador.getId())
                .veiculo(entregador.getVeiculo())
                .status(entregador.getStatus())
                .disponivel(entregador.getDisponivel())
                .build();
                

    }
    
}

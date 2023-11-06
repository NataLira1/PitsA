package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.exception.entregador.EntregadorNotAssotionException;
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

    EntregadorV1FilaService entregadorFilaService;


    @Override
    public EntregadorResponseDTO mudar(Long entregadorId, String codigoAcesso,
            EntregadorDisponibilidadeDTO entregadorDisponibilidadeDTO) {
        
            Entregador entregador = entregadorRepository.findById(entregadorId).orElseThrow(EntregadorNaoEncontradoException::new);

            if(entregador.getEstabelecimento() == null){
                throw new EntregadorNotAssotionException();
            }

            if(!entregador.getCodigoAcesso().equals(codigoAcesso)){
                throw new CodigoAcessoInvalidException();
            }

            if(!entregadorDisponibilidadeDTO.getDisponibilidade().toUpperCase().equals("DESCANSO") && !entregadorDisponibilidadeDTO.getDisponibilidade().toUpperCase().equals("ATIVO")){
                throw new EntregadorDisponibilidadeInvalida();   
            }

            entregador.setDisponivel(entregadorDisponibilidadeDTO.getDisponibilidade());
            if (entregador.getDisponivel().equals("Ativo")) {
                entregadorFilaService.adicionarFila(entregador);
            }
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

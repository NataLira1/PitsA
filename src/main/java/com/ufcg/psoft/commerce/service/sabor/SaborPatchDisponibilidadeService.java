package com.ufcg.psoft.commerce.service.sabor;

import com.ufcg.psoft.commerce.dto.sabor.SaborPatchDisponibilidadeDTO;

public interface SaborPatchDisponibilidadeService {


    Object alterarDisponibilidade(Long idEstabelecimento, Long idSabor, String codigoDeAcesso, SaborPatchDisponibilidadeDTO saborPatchDisponibilidadeDTO);
}

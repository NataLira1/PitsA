package com.ufcg.psoft.commerce.service.EstabelecimentoServices;

import com.ufcg.psoft.commerce.dto.sabor.SaborPatchDisponibilidadeDTO;
import com.ufcg.psoft.commerce.models.Sabor;

public interface EstabelecimentoPatchDisponibilidadeSaborService {


    Sabor alterarDisponibilidade(Long idEstabelecimento, Long idSabor, String codigoDeAcesso, SaborPatchDisponibilidadeDTO saborPatchDisponibilidadeDTO);
}

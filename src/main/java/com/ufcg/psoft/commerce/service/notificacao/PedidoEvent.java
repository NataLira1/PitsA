package com.ufcg.psoft.commerce.service.notificacao;

import com.ufcg.psoft.commerce.models.Entregador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoEvent {
    private Long pedido_id;
    private Entregador entregador;
}

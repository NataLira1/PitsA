package com.ufcg.psoft.commerce.models;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Pedido {

    @ManyToOne
    @JoinColumn(name = "fk_id_cliente")
    private Cliente cliente;
}

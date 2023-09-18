package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.models.Cliente;

import java.util.List;


public interface ClienteBuscarService {

    public Cliente buscar(Long id);

    public List<Cliente> buscarTudo();

}

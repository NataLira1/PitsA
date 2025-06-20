package com.ufcg.psoft.commerce.exception;

public class EstabelecimentoNaoEncontradoException  extends CommerceException{

    public EstabelecimentoNaoEncontradoException(){
        super("O estabelecimento consultado nao existe!");
    }
}

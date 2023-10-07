package com.ufcg.psoft.commerce.exception;

public class SaborNaoDisponivelException extends CommerceException {
    public SaborNaoDisponivelException(){
        super("O sabor não está disponivel");
    }
}

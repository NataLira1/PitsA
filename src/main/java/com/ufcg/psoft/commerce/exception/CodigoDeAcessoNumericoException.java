package com.ufcg.psoft.commerce.exception;

public class CodigoDeAcessoNumericoException extends  CommerceException{
    public CodigoDeAcessoNumericoException() {
        super("Codigo de acesso deve ter exatamente 6 digitos numericos");
    }
}

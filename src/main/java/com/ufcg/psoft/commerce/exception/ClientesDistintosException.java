package com.ufcg.psoft.commerce.exception;

import com.ufcg.psoft.commerce.exception.CommerceException;

public class ClientesDistintosException extends CommerceException {
    public ClientesDistintosException() {
        super("Clientes distintos!");
    }
}

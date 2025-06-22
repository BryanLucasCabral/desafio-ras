package com.desafio.api.exception;

public class CampoIncorretoException extends RuntimeException{
    public CampoIncorretoException(String mensagem) {
        super(mensagem);
    }
}

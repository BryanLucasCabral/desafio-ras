package com.desafio.api.exception;

public class ConsultaInvalidaException extends RuntimeException{

    public ConsultaInvalidaException(String mensagem){
        super(mensagem);
    }
}

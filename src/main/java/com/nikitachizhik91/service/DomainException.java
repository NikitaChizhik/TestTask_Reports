package com.nikitachizhik91.service;

public class DomainException extends Exception {

    private static final long serialVersionUID = -983316637654164037L;

    public DomainException() {
    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

}

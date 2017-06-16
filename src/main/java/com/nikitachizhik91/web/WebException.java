package com.nikitachizhik91.web;

import javax.servlet.ServletException;

public class WebException extends ServletException {

    private static final long serialVersionUID = 3352340782828981864L;

    public WebException() {
    }

    public WebException(String message) {
        super(message);
    }

    public WebException(String message, Throwable cause) {
        super(message, cause);
    }

}

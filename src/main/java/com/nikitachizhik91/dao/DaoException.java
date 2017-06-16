package com.nikitachizhik91.dao;

public class DaoException extends Exception {

    private static final long serialVersionUID = 3768777446167932125L;

    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

}

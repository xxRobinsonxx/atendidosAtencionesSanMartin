package com.salud.atendidosatenciones.exception;

public class ImportExcelException extends RuntimeException {

    public ImportExcelException(String message) {
        super(message);
    }

    public ImportExcelException(String message, Throwable cause) {
        super(message, cause);
    }
}

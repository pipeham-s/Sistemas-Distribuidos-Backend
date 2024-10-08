package com.example.backend_sistemas_distribuidos.business.exceptions;

public class UsuarioYaExiste extends Exception {


    public UsuarioYaExiste(String message) {
        super(message);
    }

    public UsuarioYaExiste() {
    }
}

package com.example.backend_sistemas_distribuidos.business.entities.auxiliar;

// Clase auxiliar para mapear el JSON recibido en el cuerpo de la solicitud
public class LoginRequest {
    private String correo;
    private String contrasena;

    // Getters y Setters
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}

package com.example.pruebaexcel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Usuario {

    private String nombre;
    private String apellido;
    private String contacto;
    private String id;
    private String numeroId;


    public Usuario(String nombre, String apellido, String contacto, String id, String numeroId) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.contacto = contacto;
        this.id = id;
        this.numeroId = numeroId;
    }
}

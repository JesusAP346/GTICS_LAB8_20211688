package org.example.gtics_lab8_20211688.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="Eventos")
@Getter
@Setter
public class EventosEntity {

    @Id
    @Column(name = "idEventos")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEventos;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "capacidadMaxima")
    private Integer capacidadMaxima;

    @Column(name = "numeroDeReservasActuales")
    private Integer numeroDeReservasActuales;

}

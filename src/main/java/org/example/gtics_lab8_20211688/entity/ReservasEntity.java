package org.example.gtics_lab8_20211688.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Reservas")
@Getter
@Setter
public class ReservasEntity {
    @Id
    @Column(name = "idReserva")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReserva;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correo")
    private String correo;

    @Column(name = "numeroCupos")
    private int numeroCupos;

    @ManyToOne
    @JoinColumn(name = "idEvento")
    private EventosEntity eventosEntity;

}

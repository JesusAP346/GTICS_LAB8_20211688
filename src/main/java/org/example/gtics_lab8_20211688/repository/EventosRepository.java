package org.example.gtics_lab8_20211688.repository;

import org.example.gtics_lab8_20211688.entity.EventosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventosRepository extends JpaRepository<EventosEntity, Integer> {

    @Query(value = "SELECT * FROM eventos WHERE fecha = :fecha ORDER BY fecha ASC", nativeQuery = true)
    Optional<EventosEntity> listarEventosPorFecha(@Param("fecha") String fecha);

    @Query(value = "SELECT * FROM eventos ORDER BY fecha ASC", nativeQuery = true)
    List<EventosEntity> listarFormaAsc();


}

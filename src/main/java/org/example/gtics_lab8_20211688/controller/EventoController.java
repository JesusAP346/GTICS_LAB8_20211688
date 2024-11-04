package org.example.gtics_lab8_20211688.controller;

import org.example.gtics_lab8_20211688.entity.EventosEntity;
import org.example.gtics_lab8_20211688.entity.ReservasEntity;
import org.example.gtics_lab8_20211688.repository.EventosRepository;
import org.example.gtics_lab8_20211688.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
public class EventoController {

    @Autowired
    private EventosRepository eventosRepository;
    @Autowired
    private ReservaRepository reservaRepository;


    @GetMapping("/eventos")
    public List<EventosEntity> listaDeEventos() {
        return eventosRepository.findAll();
    }

    @GetMapping("/eventos/{fecha}")
    public ResponseEntity<HashMap<String, Object>> listaDeEventosFiltroFecha(@PathVariable String fecha) {
        HashMap<String, Object> respuestaJson = new HashMap<>();
        try {
            if (fecha != null && !fecha.isEmpty()) {
                Optional<EventosEntity> buscarPorFecha = eventosRepository.listarEventosPorFecha(fecha);
                if (buscarPorFecha.isPresent()) {
                    respuestaJson.put("result", "ok");
                    respuestaJson.put("evento", buscarPorFecha.get());
                } else {
                    respuestaJson.put("result", "no existe esa fecha");
                }
            } else {
                List<EventosEntity> eventos = eventosRepository.listarFormaAsc();
                respuestaJson.put("result", "ok");
                respuestaJson.put("eventos", eventos);
            }
            return ResponseEntity.ok(respuestaJson);
        } catch (Exception e) {
            respuestaJson.put("result", "error");
            respuestaJson.put("message", "Formato incorrecto");
            return ResponseEntity.badRequest().body(respuestaJson);
        }
    }

    @PostMapping("/eventos/crear")
    public ResponseEntity<HashMap<String, Object>> creacionEventos(@RequestBody EventosEntity eventos, @RequestParam(value = "fetchId",required = false) boolean fetchId){
        HashMap<String,Object> respuestaJson = new HashMap<>();

        LocalDate fechaEvento = eventos.getFecha();
        LocalDate fechaActual = LocalDate.now();
        if (fechaEvento.isBefore(fechaActual) || fechaEvento.isEqual(fechaActual)) {
            respuestaJson.put("result", "error");
            respuestaJson.put("message", "La fecha tiene que ser en el futuro");
            return ResponseEntity.badRequest().body(respuestaJson);
        }
        eventos.setNumeroDeReservasActuales(0);
        eventosRepository.save(eventos);
        if(fetchId){
            respuestaJson.put("id",eventos.getIdEventos());
        }
        respuestaJson.put("result", "ok");
        respuestaJson.put("estado","evento creado");

        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaJson);
    }

    @PostMapping("/eventos/reservas/crear")
    public ResponseEntity<HashMap<String, Object>> reservas(@RequestBody ReservasEntity reservaHecha, @RequestParam(value = "fetchId",required = false) boolean fetchId){
        HashMap<String,Object> respuestaJson = new HashMap<>();

        Optional<EventosEntity> eventoOpt = eventosRepository.findById(reservaHecha.getEventosEntity().getIdEventos());
        EventosEntity evento =  eventoOpt.get();

        boolean hayCuposDisponibles = evento.getNumeroDeReservasActuales() +  reservaHecha.getNumeroCupos() <= evento.getCapacidadMaxima();

        if (!hayCuposDisponibles) {
            respuestaJson.put("result", "error");
            respuestaJson.put("message", "No hay más espacio");
            return ResponseEntity.badRequest().body(respuestaJson);
        }else{
            reservaRepository.save(reservaHecha);
            if(fetchId){
                respuestaJson.put("id",reservaHecha.getIdReserva());
            }
            respuestaJson.put("result", "ok");
            respuestaJson.put("estado","evento creado");

            return ResponseEntity.status(HttpStatus.CREATED).body(respuestaJson);

        }

    }
}


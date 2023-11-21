package com.m21290940.Tarea.controller;

import com.m21290940.Tarea.entities.Tarea;
import com.m21290940.Tarea.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( path = "/tareas" )
public class TareaRestController {
    @Autowired
    private TareaRepository tareaRepository;

    @GetMapping
    public Iterable<Tarea> getAlltareas() {
        return tareaRepository.findAll();
    }

    @GetMapping( path = "/{id}" )
    public Tarea getTareaById(@PathVariable long id) {
        Optional<Tarea> tarea = tareaRepository.findById(id);

        return  tarea.orElse(null);

        /*if ( tarea.isPresent() )
            return tarea.get();*/

        //return null;
    }

    @PostMapping
    public ResponseEntity<Tarea> registrarTarea(@RequestBody Tarea tarea) {
        boolean isOk = true;
        if (tarea == null)
            return ResponseEntity.badRequest().build();

        if (    tarea.getTitulo() == null || tarea.getDescripcion() == null ||
                tarea.getTitulo().isEmpty() || tarea.getDescripcion().isEmpty() ) {
            isOk = false;
        }
        if ( tarea.getStatus() == null || tarea.getStatus().isEmpty() )
            tarea.setStatus("Pendiente");

        if ( isOk ){
            Tarea saved = tareaRepository.save(tarea);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(path = "/id")
    public ResponseEntity<Tarea> deleteTarea(@PathVariable long id){
        Optional<Tarea> tareaInDb = tareaRepository.findById(id);
        if (tareaInDb.isEmpty() ){
            return  ResponseEntity.badRequest().build();
        }
        tareaRepository.deleteById(id);
        return  ResponseEntity.ok(tareaInDb.get());
    }
}

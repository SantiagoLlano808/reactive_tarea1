package edu.co.reactivo.tarea1.controller;

import edu.co.reactivo.tarea1.model.Almacen;
import edu.co.reactivo.tarea1.service.AlmacenService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/almacen")
public class AlmacenController {

    AlmacenService almacenService;

    public AlmacenController(AlmacenService almacenService) {
        this.almacenService = almacenService;
    }

    @GetMapping("/{id}")
    public Mono<Almacen> getAlmacenById(@PathVariable Integer id) {
        return almacenService.findById(id);
    }

    @GetMapping("/")
    public Flux<Almacen> getAllAlmacenes() {
        return almacenService.findAll();
    }

    @PostMapping("/")
    public Mono<Almacen> createAlmacen(@RequestBody Almacen almacen){
        return almacenService.save(almacen);
    }
}

package edu.co.reactivo.tarea1.controller;


import edu.co.reactivo.tarea1.model.EntregaProveedor;
import edu.co.reactivo.tarea1.service.EntregaProveedorService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/entrega")
public class EntregaProveedorController {

    EntregaProveedorService entregaProveedorService;

    public EntregaProveedorController(EntregaProveedorService entregaProveedorService) {
        this.entregaProveedorService = entregaProveedorService;
    }

    @GetMapping("/{id}")
    public Mono<EntregaProveedor> getEntregaProveedorById(@PathVariable Integer id) {
        return entregaProveedorService.findById(id);
    }

    @GetMapping("/")
    public Flux<EntregaProveedor> getAllEntregasProveedor() {
        return entregaProveedorService.findAll();
    }

    @PostMapping("/")
    public Mono<EntregaProveedor> createEntregaProveedor(@RequestBody EntregaProveedor entregaProveedor){
        return entregaProveedorService.save(entregaProveedor);
    }
}

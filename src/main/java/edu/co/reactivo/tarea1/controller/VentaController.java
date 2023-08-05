package edu.co.reactivo.tarea1.controller;



import edu.co.reactivo.tarea1.model.Venta;
import edu.co.reactivo.tarea1.service.VentaService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/venta")
public class VentaController {

    VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping("/{id}")
    public Mono<Venta> getVentaById(@PathVariable Integer id) {
        return ventaService.findById(id);
    }

    @GetMapping("/")
    public Flux<Venta> getAllVentas() {
        return ventaService.findAll();
    }

    @PostMapping("/")
    public Mono<Venta> createVenta(@RequestBody Venta venta){
        return ventaService.save(venta);
    }
}

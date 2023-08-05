package edu.co.reactivo.tarea1.controller;

import edu.co.reactivo.tarea1.model.Producto;
import edu.co.reactivo.tarea1.service.AlmacenService;
import edu.co.reactivo.tarea1.service.ProductoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/{id}")
    public Mono<Producto> getProductoById(@PathVariable Integer id) {
        return productoService.findById(id);
    }

    @GetMapping("/")
    public Flux<Producto> getAllProductos() {
        return productoService.findAll();
    }

    @PostMapping("/")
    public Mono<Producto> createProducto(@RequestBody Producto producto){ return productoService.save(producto);}

    @PutMapping("/{id}")
    public Mono<Producto> updateProducto(@PathVariable Integer id, @RequestBody Producto producto){
        return productoService.update(id, producto);
    }

    @DeleteMapping("/{id}")
    public Mono<Producto> deleteProductoById(@PathVariable Integer id){
        return productoService.deleteById(id);
    }

    @DeleteMapping("/")
    public Mono<Void> deleteAllProductos(){
        return productoService.deleteAll();
    }
}

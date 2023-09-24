package edu.co.reactivo.tarea1.controller.v2;

import edu.co.reactivo.tarea1.dto.DTOProductoDescripcionActivo;
import edu.co.reactivo.tarea1.model.Producto;
import edu.co.reactivo.tarea1.service.v2.ProductoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v2/producto")
public class ProductoController {
    ProductoService productoService;
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/inactivos")
    public Flux<Producto> getCreditosInactivos() {
        return productoService.findInactivos();
    }

    @PostMapping("/busqueda-por-descripcion")
    public Flux<Producto> getCreditosByDescripcion(@RequestBody DTOProductoDescripcionActivo dtoProductoDescripcionActivo) {
        return productoService.findByDescripcion(dtoProductoDescripcionActivo.descripcion());
    }
}

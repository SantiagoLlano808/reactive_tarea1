package edu.co.reactivo.tarea1.service;


import edu.co.reactivo.tarea1.model.Producto;
import edu.co.reactivo.tarea1.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    public Mono<Producto> findById(Integer id) {
        return productoRepository.findById(id)
                .onErrorResume(throwable -> {
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Producto con id="+ id +" no registrado").getMostSpecificCause()));
    }

    public Flux<Producto> findAll() {
        return productoRepository.findAll()
                .onErrorResume(throwable -> {
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ningun producto registrado").getMostSpecificCause()));
    }

    public Mono<Producto> save(Producto producto) {
            return productoRepository.save(producto)
                    .onErrorResume(throwable -> {
                        return Mono.empty();
                    })
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Producto no creado").getMostSpecificCause()));

    }

    public Mono<Producto> update(int id, Producto producto) {
        return productoRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
                .flatMap(optionalProducto -> {
                    if (optionalProducto.isPresent()) {
                        producto.setId(id);
                        producto.setNombre(optionalProducto.get().getNombre());
                        producto.setDescripcion(producto.getDescripcion());
                        producto.setActivo(producto.getActivo());
                        producto.setIdAlmacen(optionalProducto.get().getIdAlmacen());
                        producto.setAlmacen(optionalProducto.get().getAlmacen());
                        producto.setPrecio(producto.getPrecio());
                        producto.setCantidadInventario(optionalProducto.get().getCantidadInventario());
                        return productoRepository.save(producto)
                                .onErrorResume(throwable -> {
                                    return Mono.empty();
                                })
                                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Producto=" + producto +" no actualizado").getMostSpecificCause()));
                    }
                    return Mono.empty();
                });
    }

    public Mono<Producto> deleteById(Integer id) {
        return productoRepository.findById(id)
                .flatMap(producto -> productoRepository.deleteById(producto.getId()).thenReturn(producto))
                .onErrorResume(throwable -> {
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Producto con Id=" + id +" no borrado").getMostSpecificCause()));
    }
    public Mono<Void> deleteAll() {
        return productoRepository.deleteAll();
    }

}

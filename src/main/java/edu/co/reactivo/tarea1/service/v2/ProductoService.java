package edu.co.reactivo.tarea1.service.v2;

import edu.co.reactivo.tarea1.model.Producto;
import edu.co.reactivo.tarea1.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoService {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Flux<Producto> findInactivos() {
        return productoRepository.findByActivo(Boolean.FALSE)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al buscar productos inactivos ", throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Productos inactivos no encontrados").getMostSpecificCause()));
    }

    public Flux<Producto> findByDescripcion(String descripcion) {
        return productoRepository.findByDescripcionContaining(descripcion)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al buscar productos por descripcion: " + descripcion, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Productos con descripcion=" + descripcion + " no encontrados").getMostSpecificCause()));
    }
}

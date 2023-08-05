package edu.co.reactivo.tarea1.service;


import edu.co.reactivo.tarea1.model.EntregaProveedor;
import edu.co.reactivo.tarea1.model.Venta;
import edu.co.reactivo.tarea1.repository.EntregaProveedorRepository;
import edu.co.reactivo.tarea1.repository.VentaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }
    public Mono<Venta> findById(Integer id) {
        return ventaRepository.findById(id)
                .onErrorResume(throwable -> {
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Venta con id="+ id +" no registrada").getMostSpecificCause()));
    }

    public Flux<Venta> findAll() {
        return ventaRepository.findAll()
                .onErrorResume(throwable -> {
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ninguna venta registrada").getMostSpecificCause()));
    }

    public Mono<Venta> save(Venta venta) {
            return ventaRepository.save(venta)
                    .onErrorResume(throwable -> {
                        return Mono.empty();
                    })
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Venta no registrada").getMostSpecificCause()));

    }

}

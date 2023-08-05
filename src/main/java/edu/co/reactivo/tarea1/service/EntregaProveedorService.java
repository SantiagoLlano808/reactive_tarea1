package edu.co.reactivo.tarea1.service;


import edu.co.reactivo.tarea1.model.EntregaProveedor;
import edu.co.reactivo.tarea1.repository.EntregaProveedorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EntregaProveedorService {

    private final EntregaProveedorRepository entregaProveedorRepository;

    public EntregaProveedorService(EntregaProveedorRepository entregaProveedorRepository) {
        this.entregaProveedorRepository = entregaProveedorRepository;
    }
    public Mono<EntregaProveedor> findById(Integer id) {
        return entregaProveedorRepository.findById(id)
                .onErrorResume(throwable -> {
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Entraga con id="+ id +" no registrada").getMostSpecificCause()));
    }

    public Flux<EntregaProveedor> findAll() {
        return entregaProveedorRepository.findAll()
                .onErrorResume(throwable -> {
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ninguna entrega registrada").getMostSpecificCause()));
    }

    public Mono<EntregaProveedor> save(EntregaProveedor entregaProveedor) {
            return entregaProveedorRepository.save(entregaProveedor)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Entrega no registrada").getMostSpecificCause()));

    }

}

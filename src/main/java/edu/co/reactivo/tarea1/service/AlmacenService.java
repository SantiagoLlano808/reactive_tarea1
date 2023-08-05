package edu.co.reactivo.tarea1.service;

import edu.co.reactivo.tarea1.model.Almacen;
import edu.co.reactivo.tarea1.repository.AlmacenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AlmacenService {

    private final AlmacenRepository almacenRepository;

    public AlmacenService(AlmacenRepository almacenRepository) {
        this.almacenRepository = almacenRepository;
    }
    public Mono<Almacen> findById(Integer id) {
        return almacenRepository.findById(id)
                .onErrorResume(throwable -> {
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Almacen con id="+ id +" no registrado").getMostSpecificCause()));
    }

    public Flux<Almacen> findAll() {
        return almacenRepository.findAll()
                .onErrorResume(throwable -> {
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ningun almacen registrado").getMostSpecificCause()));
    }

    public Mono<Almacen> save(Almacen almacen) {
            return almacenRepository.save(almacen)
                    .onErrorResume(throwable -> {
                        return Mono.empty();
                    })
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Almacen no registrado").getMostSpecificCause()));

    }

}

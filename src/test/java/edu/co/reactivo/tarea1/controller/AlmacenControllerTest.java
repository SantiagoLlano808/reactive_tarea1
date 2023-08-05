package edu.co.reactivo.tarea1.controller;


import edu.co.reactivo.tarea1.model.Almacen;
import edu.co.reactivo.tarea1.model.Producto;
import edu.co.reactivo.tarea1.service.AlmacenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlmacenControllerTest {

    @Mock
    private AlmacenService almacenService;

    @InjectMocks
    private AlmacenController almacenController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAlmacenByIdExitoso() throws Exception {
        int id = 8;
        Almacen almacenEsperado = new Almacen(id, "Test almacen", "prueba",
                true);
        when(almacenService.findById(any())).thenReturn(Mono.just(almacenEsperado));
        Mono<Almacen> resultado = almacenController.getAlmacenById(id);
        assertEquals(almacenEsperado, resultado.block());
    }

    @Test
    public void testGetProductoByIdNoEncontrado() {
        int id = 300;
        when(almacenService.findById(id)).thenReturn(Mono.empty());
        when(almacenService.findById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Almacen not found"));
        assertThrows(ResponseStatusException.class, () -> almacenController.getAlmacenById(id).block());
    }

    @Test
    void testGetAllAlmacenesExitoso() {
        Almacen almacenEsperado = new Almacen(90, "Test almacen", "prueba",
                true);
        Almacen almacenEsperado2 = new Almacen(20, "Test almacen", "prueba",
                true);
        Flux<Almacen> expected = Flux.just(almacenEsperado, almacenEsperado2);
        when(almacenService.findAll()).thenReturn(expected);
        Flux<Almacen> resultado = almacenController.getAllAlmacenes();
        resultado.subscribe();
        assertEquals(expected, resultado);
    }

    @Test
    void createAlmacenExitoso() {
        Almacen almacenEsperado = new Almacen(90, "Test almacen", "prueba",
                true);
        when(almacenService.save(any(Almacen.class))).thenReturn(Mono.just(almacenEsperado));

        Mono<Almacen> result = almacenController.createAlmacen(almacenEsperado);

        StepVerifier.create(result)
                .expectNext(almacenEsperado)
                .verifyComplete();
    }

    @Test
    public void testCreateAlmacenFallido() {
        Almacen almacenEsperado = new Almacen(90, "Test almacen", "prueba",
                true);
        when(almacenService.save(any(Almacen.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Almacen no creado")));

        StepVerifier.create(almacenController.createAlmacen(almacenEsperado))
                .expectError(ResponseStatusException.class)
                .verify();
    }

}

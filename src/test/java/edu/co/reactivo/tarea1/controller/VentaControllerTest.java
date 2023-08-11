package edu.co.reactivo.tarea1.controller;



import edu.co.reactivo.tarea1.model.Venta;
import edu.co.reactivo.tarea1.service.VentaService;
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
class VentaControllerTest {

    @Mock
    private VentaService ventaService;

    @InjectMocks
    private VentaController ventaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetVentaByIdExitoso() throws Exception {
        int id = 8;
        Venta ventaEsperado = new Venta(id, "Test venta", 1,
                150000.00,1);
        when(ventaService.findById(any())).thenReturn(Mono.just(ventaEsperado));
        Mono<Venta> resultado = ventaController.getVentaById(id);
        assertEquals(ventaEsperado, resultado.block());
    }

    @Test
    public void testGetVentaByIdNoEncontrado() {
        int id = 300;
        when(ventaService.findById(id)).thenReturn(Mono.empty());
        when(ventaService.findById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta not found"));
        assertThrows(ResponseStatusException.class, () -> ventaController.getVentaById(id).block());
    }

    @Test
    void testGetAllVentasExitoso() {
        Venta ventaEsperado = new Venta(56, "Test venta", 1,
                150000.00,1);
        Venta ventaEsperado2 = new Venta(59, "Test venta", 12,
                150000.00,2);
        Flux<Venta> expected = Flux.just(ventaEsperado, ventaEsperado2);
        when(ventaService.findAll()).thenReturn(expected);
        Flux<Venta> resultado = ventaController.getAllVentas();
        resultado.subscribe();
        assertEquals(expected, resultado);
    }

    @Test
    void createVentaExitoso() {
        Venta ventaEsperado = new Venta(56, "Test venta", 1,
                150000.00,1);
        when(ventaService.save(any(Venta.class))).thenReturn(Mono.just(ventaEsperado));

        Mono<Venta> result = ventaController.createVenta(ventaEsperado);

        StepVerifier.create(result)
                .expectNext(ventaEsperado)
                .verifyComplete();
    }

    @Test
    public void testCreateVentaFallido() {
        Venta ventaEsperado = new Venta(56, "Test venta", 1,
                150000.00,1);
        when(ventaService.save(any(Venta.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Venta no creada")));

        StepVerifier.create(ventaController.createVenta(ventaEsperado))
                .expectError(ResponseStatusException.class)
                .verify();
    }

}

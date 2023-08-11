package edu.co.reactivo.tarea1.controller;




import edu.co.reactivo.tarea1.model.EntregaProveedor;
import edu.co.reactivo.tarea1.service.EntregaProveedorService;
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
class EntregaProveedorControllerTest {

    @Mock
    private EntregaProveedorService entregaProveedorService;

    @InjectMocks
    private EntregaProveedorController entregaProveedorController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEntregaByIdExitoso() throws Exception {
        int id = 8;
        EntregaProveedor entregaEsperado = new EntregaProveedor(id, "Test entrega", 1,
                150000.00,1);
        when(entregaProveedorService.findById(any())).thenReturn(Mono.just(entregaEsperado));
        Mono<EntregaProveedor> resultado = entregaProveedorController.getEntregaProveedorById(id);
        assertEquals(entregaEsperado, resultado.block());
    }

    @Test
    public void testGetEntregaByIdNoEncontrado() {
        int id = 300;
        when(entregaProveedorService.findById(id)).thenReturn(Mono.empty());
        when(entregaProveedorService.findById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "entrega not found"));
        assertThrows(ResponseStatusException.class, () -> entregaProveedorController.getEntregaProveedorById(id).block());
    }

    @Test
    void testGetAllVentasExitoso() {
        EntregaProveedor entregaEsperado = new EntregaProveedor(89, "Test entrega", 1,
                150000.00,1);
        EntregaProveedor entregaEsperado2 = new EntregaProveedor(90, "Test entrega", 2,
                150000.00,2);
        Flux<EntregaProveedor> expected = Flux.just(entregaEsperado, entregaEsperado2);
        when(entregaProveedorService.findAll()).thenReturn(expected);
        Flux<EntregaProveedor> resultado = entregaProveedorController.getAllEntregasProveedor();
        resultado.subscribe();
        assertEquals(expected, resultado);
    }

    @Test
    void createEntregaExitoso() {
        EntregaProveedor entregaProveedor = new EntregaProveedor(56, "Test entrega", 1,
                150000.00,1);
        when(entregaProveedorService.save(any(EntregaProveedor.class))).thenReturn(Mono.just(entregaProveedor));

        Mono<EntregaProveedor> result = entregaProveedorController.createEntregaProveedor(entregaProveedor);

        StepVerifier.create(result)
                .expectNext(entregaProveedor)
                .verifyComplete();
    }

    @Test
    public void testCreateEntregaFallido() {
        EntregaProveedor entregaProveedor = new EntregaProveedor(56, "Test entrega", 1,
                150000.00,1);
        when(entregaProveedorService.save(any(EntregaProveedor.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Entrega no registrada")));

        StepVerifier.create(entregaProveedorController.createEntregaProveedor(entregaProveedor))
                .expectError(ResponseStatusException.class)
                .verify();
    }

}

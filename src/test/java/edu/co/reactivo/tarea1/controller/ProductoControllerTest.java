package edu.co.reactivo.tarea1.controller;


import edu.co.reactivo.tarea1.model.Producto;
import edu.co.reactivo.tarea1.service.ProductoService;
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
class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductoByIdExitoso() throws Exception {
        int id = 8;
        Producto productoEsperado = new Producto(id, "Test Producto", "prueba",
                true, 1, "test", 1000D, 10);
        when(productoService.findById(any())).thenReturn(Mono.just(productoEsperado));
        Mono<Producto> resultado = productoController.getProductoById(id);
        assertEquals(productoEsperado, resultado.block());
    }

    @Test
    public void testGetProductoByIdNoEncontrado() {
        int id = 300;
        when(productoService.findById(id)).thenReturn(Mono.empty());
        when(productoService.findById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto not found"));
        assertThrows(ResponseStatusException.class, () -> productoController.getProductoById(id).block());
    }

    @Test
    void testGetAllProductosExitoso() {
        Producto productoEsperado = new Producto(52, "Test Producto", "prueba",
                true, 1, "test", 1000D, 10);
        Producto productoEsperado2 = new Producto(53, "Test Producto", "prueba",
                true, 1, "test", 1000D, 10);
        Flux<Producto> expected = Flux.just(productoEsperado, productoEsperado2);
        when(productoService.findAll()).thenReturn(expected);
        Flux<Producto> resultado = productoController.getAllProductos();
        resultado.subscribe();
        assertEquals(expected, resultado);
    }

    @Test
    void createProductoExitoso() {
        Producto productoEsperado = new Producto(53, "Test Producto", "prueba",
                true, 1, "test", 1000D, 10);
        when(productoService.save(any(Producto.class))).thenReturn(Mono.just(productoEsperado));

        Mono<Producto> result = productoController.createProducto(productoEsperado);

        StepVerifier.create(result)
                .expectNext(productoEsperado)
                .verifyComplete();
    }

    @Test
    public void testCreateProductoFallido() {
        Producto productoEsperado = new Producto(53, "Test Producto", "prueba",
                true, 1, "test", 1000D, 10);
        when(productoService.save(any(Producto.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Producto no creado")));

        StepVerifier.create(productoController.createProducto(productoEsperado))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testUpdateProductoExitoso() {
        Producto productoActualizado = new Producto(53, "Test Producto", "prueba",
                true, 1, "test", 1000D, 10);

        when(productoService.update(any(Integer.class), any(Producto.class))).thenReturn(Mono.just(productoActualizado));

        Mono<Producto> result = productoController.updateProducto(productoActualizado.getId(), productoActualizado);

        StepVerifier.create(result)
                .expectNext(productoActualizado)
                .verifyComplete();
    }

    @Test
    public void testUpdateProductoFallido() {
        Producto productoActualizado = new Producto(53, "Test Producto", "prueba",
                true, 1, "test", 1000D, 10);
        when(productoService.update(any(Integer.class), any(Producto.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Producto no actualizado")));

        StepVerifier.create(productoController.updateProducto(productoActualizado.getId(), productoActualizado))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testDeleteProductoByIdExitoso() throws Exception {
        int id = 56;
        Producto producto = new Producto(id, "Test Producto", "prueba",
                true, 1, "test", 1000D, 10);
        when(productoService.deleteById(any())).thenReturn(Mono.just(producto));
        Mono<Producto> resultado = productoController.deleteProductoById(id);
        assertEquals(producto, resultado.block());
    }

    @Test
    public void testDeleteProductoByIdFallido() {
        int id = 100;
        when(productoService.deleteById(id)).thenReturn(Mono.empty());
        when(productoService.deleteById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Producto no borrado"));
        assertThrows(ResponseStatusException.class, () -> productoController.deleteProductoById(id).block());
    }

    @Test
    void testDeleteAllProductosExitoso() {
        when(productoService.deleteAll()).thenReturn(Mono.empty());
        Mono<Void> resultado = productoController.deleteAllProductos();
        resultado.subscribe();
        assertEquals(Mono.empty(), resultado);
    }
}

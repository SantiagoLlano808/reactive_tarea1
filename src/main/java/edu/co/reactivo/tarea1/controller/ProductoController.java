package edu.co.reactivo.tarea1.controller;

import edu.co.reactivo.tarea1.model.Producto;
import edu.co.reactivo.tarea1.service.ProductoKafkaConsumerService;
import edu.co.reactivo.tarea1.service.ProductoSQSService;
import edu.co.reactivo.tarea1.service.ProductoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    ProductoService productoService;
    ProductoKafkaConsumerService productoKafkaConsumerService;
    ProductoSQSService productoSQSService;

    public ProductoController(ProductoService productoService,
                              ProductoKafkaConsumerService productoKafkaConsumerService,
                              ProductoSQSService productoSQSService) {
        this.productoService = productoService;
        this.productoKafkaConsumerService = productoKafkaConsumerService;
        this.productoSQSService = productoSQSService;
    }

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/{id}")
    public Mono<Producto> getProductoById(@PathVariable Integer id) {
        return productoService.findById(id);
    }

    @GetMapping("/")
    public Flux<Producto> getAllProductos() {
        return productoService.findAll();
    }

    @PostMapping("/")
    public Mono<Producto> createProducto(@RequestBody Producto producto){ return productoService.save(producto);}

    @PutMapping("/{id}")
    public Mono<Producto> updateProducto(@PathVariable Integer id, @RequestBody Producto producto){
        return productoService.update(id, producto);
    }

    @DeleteMapping("/{id}")
    public Mono<Producto> deleteProductoById(@PathVariable Integer id){
        return productoService.deleteById(id);
    }

    @DeleteMapping("/")
    public Mono<Void> deleteAllProductos(){
        return productoService.deleteAll();
    }

    @GetMapping("/topico-kakfa/{topico}")
    public Mono<String> getProductoFromTopicoKafka(@PathVariable String topico) {
        return Mono.just(productoKafkaConsumerService.obtenerUltimoProducto(topico));
    }

    @PostMapping("/aws/createQueue")
    public Mono<String> postCreateQueue(@RequestBody Map<String, Object> requestBody){
        return Mono.just(productoSQSService.createStandardQueue((String) requestBody.get("queueName")));
    }

    @PostMapping("/aws/postMessageQueue/{queueName}")
    public Mono<String> postMessageQueue(@RequestBody Producto producto, @PathVariable String queueName){
        return Mono.just(productoSQSService.publishStandardQueueMessage(
                queueName,
                2,
                producto));
    }

    @PostMapping("/aws/processCreditoByDescripcion")
    public Mono<Producto> deleteCreditoFromQueueByDescripcion(@RequestBody Map<String, Object> requestBody){
        return productoSQSService.deleteProductoMessageInQueue((String) requestBody.get("queueName"),
                (Integer) requestBody.get("maxNumberMessages"),
                (Integer) requestBody.get("waitTimeSeconds"),
                (String) requestBody.get("descripcionCredito"));
    }
}

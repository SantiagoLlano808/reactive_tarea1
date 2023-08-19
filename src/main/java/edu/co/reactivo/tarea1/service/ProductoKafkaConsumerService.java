package edu.co.reactivo.tarea1.service;

import edu.co.reactivo.tarea1.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProductoKafkaConsumerService {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoKafkaConsumerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProductoKafkaConsumerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String obtenerUltimoProducto(String topico){
        ConsumerRecord<String, String> ultimoProducto;
        KafkaConfig kafkaConfig = new KafkaConfig();
        kafkaTemplate.setConsumerFactory(kafkaConfig.consumerFactory());
        ultimoProducto = kafkaTemplate.receive(topico, 0, 0);
        String productoRecibido = Objects.requireNonNull(ultimoProducto.value());
        return productoRecibido;
    }
}

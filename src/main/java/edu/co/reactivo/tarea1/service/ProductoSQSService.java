package edu.co.reactivo.tarea1.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import edu.co.reactivo.tarea1.model.Producto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductoSQSService {
    private final AmazonSQS clienteSQS;

    public ProductoSQSService(AmazonSQS clienteSQS) {
        this.clienteSQS = clienteSQS;
    }

    public String createStandardQueue(String queueName){

        CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
        return clienteSQS.createQueue(createQueueRequest).getQueueUrl();
    }

    private String getQueueUrl(String queueName){
        return clienteSQS.getQueueUrl(queueName).getQueueUrl();
    }

    public String publishStandardQueueMessage(String queueName, Integer delaySeconds, Producto producto){
        Map<String, MessageAttributeValue> atributosMensaje = new HashMap<>();

        atributosMensaje.put("id",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(producto.getId()).orElse(-301).toString())
                        .withDataType("Number"));
        atributosMensaje.put("nombre",
                new MessageAttributeValue()
                        .withStringValue(producto.getNombre())
                        .withDataType("String"));
        atributosMensaje.put("descripcion",
                new MessageAttributeValue()
                        .withStringValue(producto.getDescripcion())
                        .withDataType("String"));
        atributosMensaje.put("activo",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(producto.getActivo()).orElse(Boolean.FALSE).toString())
                        .withDataType("String"));
        atributosMensaje.put("idAlmacen",
                new MessageAttributeValue()
                        .withStringValue(producto.getIdAlmacen().toString())
                        .withDataType("String"));
        atributosMensaje.put("almacen",
                new MessageAttributeValue()
                        .withStringValue(producto.getAlmacen())
                        .withDataType("String"));
        atributosMensaje.put("precio",
                new MessageAttributeValue()
                        .withStringValue(producto.getPrecio().toString())
                        .withDataType("String"));
        atributosMensaje.put("cantidad",
                new MessageAttributeValue()
                        .withStringValue(producto.getCantidadInventario().toString())
                        .withDataType("String"));

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(this.getQueueUrl(queueName))
                .withMessageBody(producto.getDescripcion())
                .withDelaySeconds(delaySeconds)
                .withMessageAttributes(atributosMensaje);

        return clienteSQS.sendMessage(sendMessageRequest).getMessageId();
    }

    public void publishStandardQueueMessage(String queueName, Integer delaySeconds, List<Producto> productos){
        for (Producto producto : productos){
            publishStandardQueueMessage(queueName, delaySeconds, producto);
        }
    }

    private List<Message> receiveMessagesFromQueue(String queueName, Integer maxNumberMessages, Integer waitTimeSeconds){
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(this.getQueueUrl(queueName))
                .withMaxNumberOfMessages(maxNumberMessages)
                .withMessageAttributeNames(List.of("All"))
                .withWaitTimeSeconds(waitTimeSeconds);
        return clienteSQS.receiveMessage(receiveMessageRequest).getMessages();
    }

    public Mono<Producto> deleteProductoMessageInQueue(String queueName, Integer maxNumberMessages,
                                                     Integer waitTimeSeconds, String descripcionProducto){
        List<Message> productoMessages = receiveMessagesFromQueue(queueName, maxNumberMessages, waitTimeSeconds);
        for(Message message : productoMessages){
            if(!message.getMessageAttributes().isEmpty()) {
                if (message.getMessageAttributes().get("descripcion").getStringValue().equals(descripcionProducto)) {
                    Producto producto = new Producto(Integer.valueOf(message.getMessageAttributes().get("id").getStringValue()),
                            message.getMessageAttributes().get("nombre").getStringValue(),
                            message.getMessageAttributes().get("descripcion").getStringValue(),
                            Boolean.getBoolean(message.getMessageAttributes().get("activo").getStringValue()),
                            Integer.valueOf(message.getMessageAttributes().get("idAlmacen").getStringValue()),
                            message.getMessageAttributes().get("almacen").getStringValue(),
                            Double.valueOf(message.getMessageAttributes().get("precio").getStringValue()),
                            Integer.valueOf(message.getMessageAttributes().get("cantidad").getStringValue()));
                    clienteSQS.deleteMessage(this.getQueueUrl(queueName), message.getReceiptHandle());
                    return Mono.just(producto);
                }
            }
        }
        return Mono.empty();
    }
}

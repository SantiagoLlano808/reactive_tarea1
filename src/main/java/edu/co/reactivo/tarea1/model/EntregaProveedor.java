package edu.co.reactivo.tarea1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class EntregaProveedor implements AccionesEntrega {
    @Id
    private Integer id;
    private String nombreProducto;
    private Integer idProducto;
    private Double precioCompra;
    private Integer cantidadPedida;
}

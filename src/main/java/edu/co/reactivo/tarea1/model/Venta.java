package edu.co.reactivo.tarea1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Venta {
    @Id
    private Integer id;
    private String nombreProducto;
    private Integer idProducto;
    private Double precioVenta;
    private Integer cantidadVendida;
}

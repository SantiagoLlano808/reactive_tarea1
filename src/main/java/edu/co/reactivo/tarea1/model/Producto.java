package edu.co.reactivo.tarea1.model;

import lombok.*;
import org.springframework.data.annotation.Id;
@Data
@AllArgsConstructor
public class Producto {
    @Id
    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private Integer idAlmacen;
    private String almacen;
    private Double precio;
    private Integer cantidadInventario;
}

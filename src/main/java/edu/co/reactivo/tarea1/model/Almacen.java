package edu.co.reactivo.tarea1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Almacen {
    @Id
    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}

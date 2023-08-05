package edu.co.reactivo.tarea1.repository;


import edu.co.reactivo.tarea1.model.Venta;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends R2dbcRepository<Venta, Integer> {

}



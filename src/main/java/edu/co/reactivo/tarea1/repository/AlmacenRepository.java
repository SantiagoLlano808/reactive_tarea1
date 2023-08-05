package edu.co.reactivo.tarea1.repository;


import edu.co.reactivo.tarea1.model.Almacen;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlmacenRepository extends R2dbcRepository<Almacen, Integer> {

}



package edu.co.reactivo.tarea1.repository;


import edu.co.reactivo.tarea1.model.EntregaProveedor;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregaProveedorRepository extends R2dbcRepository<EntregaProveedor, Integer> {

}



package edu.co.reactivo.tarea1.repository;



import edu.co.reactivo.tarea1.model.Producto;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductoRepository extends R2dbcRepository<Producto, Integer> {
    Flux<Producto> findByActivo(boolean activo);
    Flux<Producto> findByDescripcionContaining(String descripcion);
}



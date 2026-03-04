package edu.comillas.icai.gitt.pat.spring.grupo1.repositorio;

import edu.comillas.icai.gitt.pat.spring.grupo1.modelo.LineaCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LineaCarritoRepositorio extends JpaRepository<LineaCarrito, Long> {
    Optional<LineaCarrito> findByCarrito_IdCarritoAndIdArticulo(int idCarrito, int idArticulo);
}
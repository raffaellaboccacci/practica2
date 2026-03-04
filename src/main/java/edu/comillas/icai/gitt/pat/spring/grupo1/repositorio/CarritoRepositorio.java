package edu.comillas.icai.gitt.pat.spring.grupo1.repositorio;

import edu.comillas.icai.gitt.pat.spring.grupo1.modelo.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepositorio extends JpaRepository<Carrito, Integer> {
}
package edu.comillas.icai.gitt.pat.spring.grupo1.servicio;

import edu.comillas.icai.gitt.pat.spring.grupo1.modelo.Carrito;
import edu.comillas.icai.gitt.pat.spring.grupo1.modelo.LineaCarrito;
import edu.comillas.icai.gitt.pat.spring.grupo1.repositorio.CarritoRepositorio;
import edu.comillas.icai.gitt.pat.spring.grupo1.repositorio.LineaCarritoRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarritoServicio {

    private final CarritoRepositorio carritoRepositorio;
    private final LineaCarritoRepositorio lineaCarritoRepositorio;

    public CarritoServicio(CarritoRepositorio carritoRepositorio, LineaCarritoRepositorio lineaCarritoRepositorio) {
        this.carritoRepositorio = carritoRepositorio;
        this.lineaCarritoRepositorio = lineaCarritoRepositorio;
    }

    public List<Carrito> listar() {
        return carritoRepositorio.findAll();
    }

    public Carrito obtener(int idCarrito) {
        return carritoRepositorio.findById(idCarrito)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no existe"));
    }

    public Carrito crear(Carrito carrito) {
        // total empieza en 0 y sin líneas
        carrito.setIdCarrito(null);
        carrito.setTotalPrecio(0.0);
        if (carrito.getLineas() != null) {
            carrito.getLineas().clear();
        }
        return carritoRepositorio.save(carrito);
    }

    public void borrar(int idCarrito) {
        if (!carritoRepositorio.existsById(idCarrito)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no existe");
        }
        carritoRepositorio.deleteById(idCarrito);
    }

    public Carrito modificar(int idCarrito, Carrito datos) {
        Carrito carrito = obtener(idCarrito);
        carrito.setIdUsuario(datos.getIdUsuario());
        carrito.setCorreoUsuario(datos.getCorreoUsuario());
        return carritoRepositorio.save(carrito);
    }

    // --- NUEVO: añadir línea ---
    @Transactional
    public Carrito anadirLinea(int idCarrito, LineaCarrito nueva) {
        Carrito carrito = obtener(idCarrito);

        if (nueva.getUnidades() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unidades debe ser > 0");
        }
        if (nueva.getPrecioUnitario() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Precio unitario no puede ser negativo");
        }

        // Si ya existe línea del mismo artículo, sumamos unidades (práctico)
        LineaCarrito existente = lineaCarritoRepositorio
                .findByCarrito_IdCarritoAndIdArticulo(idCarrito, nueva.getIdArticulo())
                .orElse(null);

        if (existente == null) {
            nueva.setIdLinea(null);
            nueva.setCarrito(carrito);
            nueva.recalcularCoste();
            carrito.getLineas().add(nueva);
        } else {
            existente.setPrecioUnitario(nueva.getPrecioUnitario());
            existente.setUnidades(existente.getUnidades() + nueva.getUnidades());
            existente.recalcularCoste();
        }

        carrito.recalcularTotal();
        return carritoRepositorio.save(carrito);
    }

    // --- NUEVO: borrar línea ---
    @Transactional
    public Carrito borrarLinea(int idCarrito, int idArticulo) {
        Carrito carrito = obtener(idCarrito);

        LineaCarrito linea = lineaCarritoRepositorio
                .findByCarrito_IdCarritoAndIdArticulo(idCarrito, idArticulo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Linea no existe"));

        carrito.getLineas().remove(linea); // orphanRemoval=true => se borra en BD
        carrito.recalcularTotal();
        return carritoRepositorio.save(carrito);
    }
}
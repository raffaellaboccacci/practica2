package edu.comillas.icai.gitt.pat.spring.grupo1.controlador;

import edu.comillas.icai.gitt.pat.spring.grupo1.modelo.Carrito;
import edu.comillas.icai.gitt.pat.spring.grupo1.modelo.LineaCarrito;
import edu.comillas.icai.gitt.pat.spring.grupo1.servicio.CarritoServicio;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarritoControlador {

    private final CarritoServicio carritoServicio;

    public CarritoControlador(CarritoServicio carritoServicio) {
        this.carritoServicio = carritoServicio;
    }

    @GetMapping("/api/carrito")
    public List<Carrito> getCarritos() {
        return carritoServicio.listar();
    }

    @PostMapping("/api/carrito")
    @ResponseStatus(HttpStatus.CREATED)
    public Carrito creaCarrito(@RequestBody Carrito carrito) {
        return carritoServicio.crear(carrito);
    }

    @GetMapping("/api/carrito/{idCarrito}")
    public Carrito getCarrito(@PathVariable int idCarrito) {
        return carritoServicio.obtener(idCarrito);
    }

    @DeleteMapping("/api/carrito/{idCarrito}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarCarrito(@PathVariable int idCarrito) {
        carritoServicio.borrar(idCarrito);
    }

    @PutMapping("/api/carrito/{idCarrito}")
    public Carrito modificarCarrito(@PathVariable int idCarrito, @RequestBody Carrito carrito) {
        return carritoServicio.modificar(idCarrito, carrito);
    }

    // --------- NUEVOS ENDPOINTS (líneas) ---------

    // Añadir línea al carrito
    @PostMapping("/api/carrito/{idCarrito}/lineas")
    @ResponseStatus(HttpStatus.CREATED)
    public Carrito anadirLinea(@PathVariable int idCarrito, @RequestBody LineaCarrito linea) {
        return carritoServicio.anadirLinea(idCarrito, linea);
    }

    // Borrar línea del carrito por idArticulo
    @DeleteMapping("/api/carrito/{idCarrito}/lineas/{idArticulo}")
    public Carrito borrarLinea(@PathVariable int idCarrito, @PathVariable int idArticulo) {
        return carritoServicio.borrarLinea(idCarrito, idArticulo);
    }
}
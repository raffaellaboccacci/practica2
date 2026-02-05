package edu.comillas.icai.gitt.pat.spring.grupo1.controlador;

import edu.comillas.icai.gitt.pat.spring.grupo1.modelo.Carrito;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
- Create - POST
    - POST /api/carrito (Crea el carrito)
- Read - GET
    - GET /api/carritos (me da un listado de carritos)
    - /api/carrito/<id-carrito> (devuelve descripción del 1 carrito del id que le demos)
- Update - PUT
    - /api/carrito/<id-carrito>
- Delete - DELETE
    - /api/carrito/<id-carrito> (borra el carrito)
 */

@RestController
public class CarritoControlador {
    private final Map<Integer, Carrito> carritos = new HashMap<>();

     @GetMapping("/api/carrito")
     public Collection<Carrito> getCarritos() {
//         Ejemplo que hicimos
//         Carrito demo = new Carrito(1,1, "Camiseta", 2, 39.98);
//         carritos.put("1",demo);
         return carritos.values();
     }

    @PostMapping("/api/carrito")
    @ResponseStatus(HttpStatus.CREATED)
    public Carrito creaCarrito(@RequestBody Carrito carrito) {
        carritos.put(carrito.getIdCarrito(), carrito);
        return carrito;
        }

    @GetMapping("/api/carrito/{idCarrito}")
    public Carrito getCarrito(@PathVariable int idCarrito) {
        return carritos.get(idCarrito);
    }

    @DeleteMapping("/api/carrito/{idCarrito}")
    public void borrarCarritos(@PathVariable int idCarrito) {
         carritos.remove(idCarrito);
    }

    @PutMapping("/api/carrito/{idCarrito}")
    public Carrito modificarCarrito(@PathVariable int idCarrito, @RequestBody Carrito carrito) {
         carritos.put(idCarrito, carrito);
         return carrito;
    }

}

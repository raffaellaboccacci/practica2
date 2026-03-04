package edu.comillas.icai.gitt.pat.spring.grupo1.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "lineas_carrito")
public class LineaCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLinea;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrito")
    @JsonIgnore
    private Carrito carrito;

    private int idArticulo;

    private double precioUnitario;

    private int unidades;

    private double costeLinea;

    public LineaCarrito() {
        // Constructor vacío requerido por JPA
    }

    public Long getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Long idLinea) {
        this.idLinea = idLinea;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public double getCosteLinea() {
        return costeLinea;
    }

    public void setCosteLinea(double costeLinea) {
        this.costeLinea = costeLinea;
    }

    public void recalcularCoste() {
        this.costeLinea = this.precioUnitario * this.unidades;
    }
}

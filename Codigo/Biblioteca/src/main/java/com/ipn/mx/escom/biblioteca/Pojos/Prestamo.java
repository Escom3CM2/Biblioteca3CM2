package com.ipn.mx.escom.biblioteca.Pojos;
// Generated 24/05/2017 04:48:03 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Prestamo generated by hbm2java
 */
public class Prestamo  implements java.io.Serializable {


     private int idPrestamo;
     private Lector lector;
     private Date fechaInicio;
     private Date fechaFin;
     private int estadoPr;
     private Set DPrestamos = new HashSet(0);

    public Prestamo() {
    }

	
    public Prestamo(int idPrestamo, Lector lector, Date fechaInicio, Date fechaFin, int estadoPr) {
        this.idPrestamo = idPrestamo;
        this.lector = lector;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoPr = estadoPr;
    }
    public Prestamo(int idPrestamo, Lector lector, Date fechaInicio, Date fechaFin, int estadoPr, Set DPrestamos) {
       this.idPrestamo = idPrestamo;
       this.lector = lector;
       this.fechaInicio = fechaInicio;
       this.fechaFin = fechaFin;
       this.estadoPr = estadoPr;
       this.DPrestamos = DPrestamos;
    }
   
    public int getIdPrestamo() {
        return this.idPrestamo;
    }
    
    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }
    public Lector getLector() {
        return this.lector;
    }
    
    public void setLector(Lector lector) {
        this.lector = lector;
    }
    public Date getFechaInicio() {
        return this.fechaInicio;
    }
    
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public Date getFechaFin() {
        return this.fechaFin;
    }
    
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    public int getEstadoPr() {
        return this.estadoPr;
    }
    
    public void setEstadoPr(int estadoPr) {
        this.estadoPr = estadoPr;
    }
    public Set getDPrestamos() {
        return this.DPrestamos;
    }
    
    public void setDPrestamos(Set DPrestamos) {
        this.DPrestamos = DPrestamos;
    }




}


